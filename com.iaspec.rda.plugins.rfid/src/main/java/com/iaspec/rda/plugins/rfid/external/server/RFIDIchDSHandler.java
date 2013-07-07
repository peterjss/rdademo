/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.external.server;


import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelFutureListener;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.Cookie;
import org.jboss.netty.handler.codec.http.CookieDecoder;
import org.jboss.netty.handler.codec.http.CookieEncoder;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.MessageRequest;
import com.iaspec.rda.plugins.rfid.entity.Session;
import com.iaspec.rda.plugins.rfid.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.rfid.external.protocol.IXmlElement;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDRequest;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDResponse;
import com.iaspec.rda.plugins.rfid.util.ExceptionMessages;
import com.iaspec.rda.plugins.rfid.util.MessageUtil;
import com.iaspec.rda.plugins.rfid.util.RdaException;


/**
 * Handler implementation for the echo server.
 */
public class RFIDIchDSHandler extends SimpleChannelUpstreamHandler implements IProtocol
{

	private static final Logger logger = LoggerFactory.getLogger(RFIDIchDSHandler.class);

	private HttpRequest request;
	private boolean readingChunks;
	private LinkedList<Integer> sequences = new LinkedList<Integer>();

	private String sessionId;
	private String currentSequence;
	private String expectedSequence;
	private IMessageDispatcher messageDispatcher;

	/**
	 * Buffer that stores the response content
	 */
	private final StringBuilder buf = new StringBuilder();

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e) throws Exception
	{
		if (e instanceof ChannelStateEvent) {
			logger.debug("Channel state changed: {}", e);
		}
		super.handleUpstream(ctx, e);
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		logger.info("Received the 'DS' message.");

		HttpRequest request = this.request = (HttpRequest) e.getMessage();

		ChannelBuffer content = request.getContent();
		if (content.readable()) {
			switchBiz(content, ctx, e);
		}
	}

	private void switchBiz(ChannelBuffer channelBuffer, final ChannelHandlerContext ctx, MessageEvent e)
	        throws Exception
	{
		RFIDRequest bizRequest = MessageUtil.decodeMessage(channelBuffer);
		if (bizRequest == null || !IXmlElement.ROOT_TYPE_REQ.equalsIgnoreCase(bizRequest.getRootTypeValue())) {
			throw new RuntimeException("Access failed.");
		}

		sessionId = bizRequest.getSessionIdValue();
		Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);

		if (session == null || !sessionId.equalsIgnoreCase(session.getId()) || !session.isAccessed()
		        || !session.isAlive()) {
			throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_SESSION);
		}

		if (!bizRequest.getDeviceIdValue().equalsIgnoreCase(session.getDeviceId())
		        || !bizRequest.getDeviceTypeValue().equalsIgnoreCase(session.getDeviceType())) {
			throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_DEVICE_OR_CONNECTION);
		}

		currentSequence = bizRequest.getSequenceValue();

		if (bizRequest.getSequenceValue() == null || !currentSequence.matches("\\d")) {
			expectedSequence = currentSequence;
			throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_SEQUENCE_NUMBER);
		}

		Integer seqNum = Integer.valueOf(currentSequence);

		if (!validSequence(seqNum)) {
			throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_SEQUENCE_NUMBER);
		}
		sequences.add(seqNum);

		logger.debug("DS Sequence is : " + bizRequest.getSequenceValue());

		logger.debug("Execute the message handler.");

		MessageRequest message = new MessageRequest();
		message.setSessionId(session.getServerId());
		// session.getServerId()
		message.setVersionNo(1);
		message.setSequenceNo(Integer.valueOf(bizRequest.getSequenceValue()));
		message.setCountinueFlag((byte) 1);
		message.setTimestamp(new Date().getTime());

		message.setMessageId(MessageRequest.generateMessageId());

		message.setMessageType(MessageRequest.MESSAGETYPE_NORMAL);
		message.setMessagePriority(MessageRequest.MESSAGEPRIORITY_DEFAULT);
		message.setMessageLength(bizRequest.getApplicationDataStructure().getBytes().length);

		message.setDeviceId(bizRequest.getDeviceIdValue());
		message.setMessageTime("2012");
		message.setEpcList(bizRequest.getApplicationDataStructure().getBytes());

		// message.sete(bizRequest.getApplicationDataStructure());
		messageDispatcher.addMessage(message);

		this.sendDAResponse(bizRequest, e.getChannel(), session);
	}

	private boolean validSequence(Integer seqNum)
	{
		if (sequences.size() > 0) {
			if (seqNum < 1 || seqNum > 32767) {
				return false;
			}
			Integer seq = sequences.getLast();
			expectedSequence = String.valueOf((seq.intValue() + 1));
			if ((seq.intValue() + 1) != seqNum) {
				return false;
			}
			return true;
		}
		return true;
	}

	private void sendDAResponse(RFIDRequest bizRequest, Channel channel, Session session)
	{
		logger.info("Send the 'DA' response to device.");
		// Session session = Session.getCurrentSession(e.getChannel());
		RFIDResponse response = new RFIDResponse();
		response.setRootTypeValue(IXmlElement.ROOT_TYPE_RSP);
		response.setServerIdValue(session.getServerId());
		response.setSessionIdValue(session.getId());
		response.setSequenceValue(bizRequest.getSequenceValue());

		ChannelBuffer con = (ChannelBuffer) MessageUtil.daEcodeMessage(response);

		buf.delete(0, buf.length());
		buf.append(con.toString(CharsetUtil.UTF_8));
		writeResponse(channel);
	}

	private void sendDNResponse(RFIDRequest bizRequest, Channel channel, Session session, String errorCode,
	        String errorDescription)
	{
		logger.info("Send the 'DN' response to device.");
		// Session session = Session.getCurrentSession(e.getChannel());

		RFIDResponse response = new RFIDResponse();
		response.setRootTypeValue(IXmlElement.ROOT_TYPE_RSP);
		response.setServerIdValue(session.getServerId());
		response.setSessionIdValue(session.getId());
		// int i = sequences.getLast();
		response.setSequenceValue(expectedSequence);

		response.setErrorValue(errorCode);
		response.setErrorDescription(errorDescription);

		ChannelBuffer con = (ChannelBuffer) MessageUtil.dnEcodeMessage(response);

		buf.delete(0, buf.length());
		buf.append(con.toString(CharsetUtil.UTF_8));
		writeResponse(channel);
	}

	private void writeResponse(Channel channel)
	{
		// Decide whether to close the connection or not.
		boolean keepAlive = isKeepAlive(request);

		// Build the response object.
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, OK);

		response.setContent(ChannelBuffers.copiedBuffer(buf.toString(), CharsetUtil.UTF_8));
		response.setHeader(CONTENT_TYPE, "text/xml; charset=UTF-8");

		if (keepAlive) {
			// Add 'Content-Length' header only for a keep-alive connection.
			response.setHeader(CONTENT_LENGTH, response.getContent().readableBytes());
			// Add keep alive header as per:
			// -
			// http://www.w3.org/Protocols/HTTP/1.1/draft-ietf-http-v11-spec-01.html#Connection
			response.setHeader(CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
		}

		// Encode the cookie.
		String cookieString = request.getHeader(COOKIE);
		if (cookieString != null) {
			CookieDecoder cookieDecoder = new CookieDecoder();
			Set<Cookie> cookies = cookieDecoder.decode(cookieString);
			if (!cookies.isEmpty()) {
				// Reset the cookies if necessary.
				CookieEncoder cookieEncoder = new CookieEncoder(true);
				for (Cookie cookie : cookies) {
					cookieEncoder.addCookie(cookie);
					response.addHeader(SET_COOKIE, cookieEncoder.encode());
				}
			}
		}

		// Write the response.
		ChannelFuture future = channel.write(response);

		// Close the non-keep-alive connection after the write operation is
		// done.
		if (!keepAlive) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		logger.error("Server side Unexpected exception from downstream." + e.getCause().getMessage(), e.getCause());
		Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);
		RFIDRequest bizRequest = new RFIDRequest();
		bizRequest.setSequenceValue(currentSequence);
		String[] m = ExceptionMessages.getExceptionMessage(e.getCause().getMessage());

		this.sendDNResponse(bizRequest, e.getChannel(), session, m[0], m[1]);
	}

	@Override
	public String getName()
	{
		return null;
	}

	public void setMessageDispatcher(IMessageDispatcher messageDispatcher)
	{
		this.messageDispatcher = messageDispatcher;
	}

}
