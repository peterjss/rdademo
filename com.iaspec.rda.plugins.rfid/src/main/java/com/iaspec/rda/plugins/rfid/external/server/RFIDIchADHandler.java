/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.external.server;


import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_LENGTH;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.COOKIE;
import static org.jboss.netty.handler.codec.http.HttpHeaders.Names.SET_COOKIE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.OK;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

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
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpHeaders;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.plugins.rfid.entity.Device;
import com.iaspec.rda.plugins.rfid.entity.Session;
import com.iaspec.rda.plugins.rfid.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.rfid.external.protocol.IXmlElement;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDRequest;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDResponse;
import com.iaspec.rda.plugins.rfid.license.LicenseReader;
import com.iaspec.rda.plugins.rfid.util.ExceptionMessages;
import com.iaspec.rda.plugins.rfid.util.MessageUtil;
import com.iaspec.rda.plugins.rfid.util.RdaException;


/**
 * Handler implementation for the echo server.
 */
public class RFIDIchADHandler extends SimpleChannelUpstreamHandler implements IProtocol
{
	private static final Logger logger = LoggerFactory.getLogger(RFIDIchADHandler.class);
	private HttpRequest request;
	private boolean readingChunks;
	private String sessionId;
	private String serverId;

	/**
	 * Buffer that stores the response content
	 */
	private final StringBuilder buf = new StringBuilder();

	public RFIDIchADHandler(IchHandlerConfig config)
	{
		this.serverId = config.getServerId();
	}

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
		logger.info("Received the 'AD' massage.");

		if (!readingChunks) {
			HttpRequest request = this.request = (HttpRequest) e.getMessage();

			if (is100ContinueExpected(request)) {
				send100Continue(e);
			}

			if (request.isChunked()) {
				readingChunks = true;
			}
			else {
				ChannelBuffer content = request.getContent();
				if (content.readable()) {
					switchBiz(content, ctx, e);
				}
			}
		}
		else {
			HttpChunk chunk = (HttpChunk) e.getMessage();
			if (chunk.isLast()) {
				readingChunks = false;
				ChannelBuffer content = ChannelBuffers.copiedBuffer(buf.toString(), CharsetUtil.UTF_8);
				switchBiz(content, ctx, e);
			}
			else {
				buf.append(chunk.getContent().toString(CharsetUtil.UTF_8));
			}
		}
	}

	private void switchBiz(ChannelBuffer channelBuffer, final ChannelHandlerContext ctx, MessageEvent e)
	        throws Exception
	{
		RFIDRequest bizRequest = MessageUtil.decodeMessage(channelBuffer);
		if (bizRequest == null || !IXmlElement.ROOT_TYPE_REQ.equalsIgnoreCase(bizRequest.getRootTypeValue())) {
			throw new RdaException("Access failed.");
		}

		sessionId = bizRequest.getSessionIdValue();
		Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);
		if (session == null || !sessionId.equalsIgnoreCase(session.getId())) {
			throw new RdaException(ExceptionMessages.EXCEPTION_INVALID_SESSION);
		}

		try {
			Device device = new Device();
			device.setId(bizRequest.getDeviceIdValue());
			device.setType(bizRequest.getDeviceTypeValue());
			LicenseReader.verifyChallengeCode(bizRequest.getChallengeValue(), session.getChallengeValue(), device);
			logger.info("The certificate is trusted.");

			this.sendAAResponse(e, session);
			logger.info("Session has been activied. Handshake success.");

			session.setAccessed(true);
			session.setAlive(true);
			session.setDeviceId(bizRequest.getDeviceIdValue());
			session.setDeviceType(bizRequest.getDeviceTypeValue());

			SessionMapDao.getInstance().save(session);
		}
		catch (RdaException re) {
			session.setAccessed(false);
			session.setAlive(false);
			throw new RdaException(re.getMessage(), re.getCause());
		}
		e.getChannel().getPipeline().remove(this);
	}

	private void sendAAResponse(MessageEvent e, Session session)
	{
		logger.info("Send the 'AA' response to device.");

		RFIDResponse response = new RFIDResponse();
		response.setRootTypeValue(IXmlElement.ROOT_TYPE_RSP);
		response.setServerIdValue(session.getServerId());
		response.setSessionIdValue(session.getId());
		ChannelBuffer con = (ChannelBuffer) MessageUtil.aaEcodeMessage(response);

		buf.delete(0, buf.length());
		buf.append(con.toString(CharsetUtil.UTF_8));
		writeResponse(e.getChannel(), false);
	}

	private void sendANResponse(Channel channel, Session session, String errorCode, String errorDescription)
	{
		logger.info("Send the 'AN' response to device.");

		RFIDResponse response = new RFIDResponse();
		response.setRootTypeValue(IXmlElement.ROOT_TYPE_RSP);
		response.setServerIdValue(session.getServerId());
		response.setSessionIdValue(session.getId());
		response.setErrorValue(errorCode);
		response.setErrorDescription(errorDescription);
		ChannelBuffer con = (ChannelBuffer) MessageUtil.anEcodeMessage(response);
		buf.delete(0, buf.length());
		buf.append(con.toString(CharsetUtil.UTF_8));
		writeResponse(channel, true);
	}

	private void writeResponse(Channel channel, boolean close)
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

		if (close) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		logger.error("Server side Unexpected exception from downstream." + e.getCause().getMessage(), e.getCause());
		Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);
		if (session == null) {
			session = new Session();
			session.setId(sessionId);
			session.setServerId(serverId);
		}
		String[] m = ExceptionMessages.getExceptionMessage(e.getCause().getMessage());
		this.sendANResponse(e.getChannel(), session, m[0], m[1]);
		e.getChannel().getPipeline().remove(this);
	}

	private static void send100Continue(MessageEvent e)
	{
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
		e.getChannel().write(response);
	}

	@Override
	public String getName()
	{
		return null;
	}
}
