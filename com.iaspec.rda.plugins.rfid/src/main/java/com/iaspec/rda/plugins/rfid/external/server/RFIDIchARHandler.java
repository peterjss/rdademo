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
import com.iaspec.rda.plugins.rfid.entity.Session;
import com.iaspec.rda.plugins.rfid.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.rfid.external.protocol.IXmlElement;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDRequest;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDResponse;
import com.iaspec.rda.plugins.rfid.util.MessageUtil;
import com.iaspec.rda.plugins.rfid.util.RdaException;


/**
 * Handler implementation for the echo server.
 */
public class RFIDIchARHandler extends SimpleChannelUpstreamHandler implements IProtocol
{

	private static final Logger logger = LoggerFactory.getLogger(RFIDIchARHandler.class);
	private HttpRequest request;
	private boolean readingChunks;
	private String sessionId;

	/**
	 * Buffer that stores the response content
	 */
	private final StringBuilder buf = new StringBuilder();

	public RFIDIchARHandler(String sessionId)
	{
		this.sessionId = sessionId;
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

		logger.info("Received the 'AR' massage.");
		if (!readingChunks) {
			HttpRequest request = this.request = (HttpRequest) e.getMessage();
			request.toString();
			if (is100ContinueExpected(request)) {
				send100Continue(e);
			}

			logger.debug("request is chunked? {}", request.isChunked());
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

		logger.debug("Device ID: {}", bizRequest.getDeviceIdValue());
		this.sendResponse(bizRequest, e);
	}

	private void sendResponse(RFIDRequest bizRequest, MessageEvent e)
	{
		logger.info("Send the 'AD' response to device.");
		Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);
		// session.setChallengeValue(Session.generateChallenge());

		RFIDResponse response = new RFIDResponse();
		response.setRootTypeValue(IXmlElement.ROOT_TYPE_RSP);
		response.setServerIdValue(session.getServerId());
		response.setSessionIdValue(session.getId());
		response.setChallengeValue(session.getChallengeValue());

		ChannelBuffer con = (ChannelBuffer) MessageUtil.acEcodeMessage(response);
		buf.delete(0, buf.length());
		buf.append(con.toString(CharsetUtil.UTF_8));
		writeResponse(e);
	}

	private void writeResponse(MessageEvent e)
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
		ChannelFuture future = e.getChannel().write(response);

		// Close the non-keep-alive connection after the write operation is
		// done.
		if (!keepAlive) {
			future.addListener(ChannelFutureListener.CLOSE);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		logger.error("Server side Unexpected exception from downstream. msg: " + e.getCause().getMessage(),
		        e.getCause());
		e.getChannel().close();
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
