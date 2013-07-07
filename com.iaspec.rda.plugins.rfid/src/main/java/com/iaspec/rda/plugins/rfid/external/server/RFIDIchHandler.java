/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.external.server;


import static org.jboss.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static org.jboss.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static org.jboss.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.codec.http.DefaultHttpResponse;
import org.jboss.netty.handler.codec.http.HttpChunk;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.HttpResponse;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.CharsetUtil;
import org.jboss.netty.util.HashedWheelTimer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.plugins.rfid.entity.Session;
import com.iaspec.rda.plugins.rfid.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.rfid.external.protocol.IXmlElement;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDRequest;
import com.iaspec.rda.plugins.rfid.util.MessageUtil;


/**
 * Handler implementation for the ICH server.
 */
public class RFIDIchHandler extends SimpleChannelUpstreamHandler implements IProtocol
{

	// private static final Logger logger =
	// Logger.getLogger(RFIDIchHandler.class.getName());
	private static final Logger logger = LoggerFactory.getLogger(RFIDIchHandler.class);

	private boolean readingChunks;
	private boolean ssl;
	private String serverId;
	private int sessionTimeout = 600;
	private IchHandlerConfig config;
	private String sessionId;
	// private String currentSessionId;
	private IMessageDispatcher messageDispatcher;

	private SessionMapDao sessionMapDao = SessionMapDao.getInstance();
	/**
	 * Buffer that stores the response content
	 */
	private final StringBuilder buf = new StringBuilder();

	public RFIDIchHandler(IchHandlerConfig config)
	{
		this.ssl = config.isSsl();
		this.serverId = config.getServerId();
		this.sessionTimeout = config.getSessionTimeout();
		this.config = config;

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
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{

		// Get the SslHandler in the current pipeline.
		// We added it in SecureChatPipelineFactory.
		if (ssl) {
			final SslHandler sslHandler = ctx.getPipeline().get(SslHandler.class);

			// Get notified when SSL handshake is done.
			ChannelFuture handshakeFuture = sslHandler.handshake();
			// handshakeFuture.addListener(new Greeter(sslHandler));
		}
	}

	@Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		// logger.info("Start switch handler.");
		if (!readingChunks) {
			HttpRequest request = (HttpRequest) e.getMessage();

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
			throw new RuntimeException("Access failed.");
		}

		// currentSessionId = bizRequest.getSessionIdValue();

		ChannelPipeline p = ctx.getPipeline();

		switch (bizRequest.getSendType())
		{
		case AR:
			if (sessionId != null) {
				Session session = sessionMapDao.getCurrentSession(sessionId);
				if (session != null) {
					sessionMapDao.remove(sessionId);
				}
			}
			Session session = new Session();
			session.setServerId(serverId);
			session.setChallengeValue(Session.generateChallenge());
			sessionId = sessionMapDao.createSession(session);

			clearPipeline(p);
			p.addLast("arhandler", new RFIDIchARHandler(sessionId));

			ctx.sendUpstream(e);
			break;
		case AD:
			clearPipeline(p);
			p.addLast("adhandler", new RFIDIchADHandler(config));
			if (p.get("dshandler") == null) {
				RFIDIchDSHandler dshandler = new RFIDIchDSHandler();
				dshandler.setMessageDispatcher(messageDispatcher);
				p.addLast("dshandler", dshandler);
				p.addBefore("dshandler", "session_timeout", new IdleStateHandler(new HashedWheelTimer(), 0, 0,
				        sessionTimeout));
				p.addBefore("dshandler", "session_hearbeat", new Heartbeat());
			}
			ctx.sendUpstream(e);
			break;
		case DS:
			// clearPipeline(p);
			if (p.get("arhandler") != null) {
				p.remove("arhandler");
			}
			if (p.get("adhandler") != null) {
				p.remove("adhandler");
			}

			if (p.get("session_timeout") != null && p.get("session_hearbeat") != null) {
				p.remove("session_timeout");
				p.remove("session_hearbeat");
			}

			if (p.get("dshandler") == null) {
				RFIDIchDSHandler dshandler = new RFIDIchDSHandler();
				dshandler.setMessageDispatcher(messageDispatcher);
				p.addLast("dshandler", dshandler);
			}

			p.addBefore("dshandler", "session_timeout", new IdleStateHandler(new HashedWheelTimer(), 0, 0,
			        sessionTimeout));
			p.addBefore("dshandler", "session_hearbeat", new Heartbeat());

			ctx.sendUpstream(e);
			break;
		case SK:
			if (p.get("skhandler") == null) {
				p.addBefore("dshandler", "skhandler", new RFIDIchSKHandler(config));
			}
			ctx.sendUpstream(e);
			break;
		case SC:
			if (p.get("schandler") == null) {
				p.addBefore("dshandler", "schandler", new RFIDIchSCHandler());
			}
			ctx.sendUpstream(e);
			break;
		}
	}

	private void clearPipeline(ChannelPipeline p)
	{
		if (p.get("arhandler") != null) {
			p.remove("arhandler");
		}
		if (p.get("adhandler") != null) {
			p.remove("adhandler");
		}
		if (p.get("dshandler") != null) {
			p.remove("dshandler");
		}
		if (p.get("skhandler") != null) {
			p.remove("skhandler");
		}
		if (p.get("session_timeout") != null) {
			p.remove("session_timeout");
		}
		if (p.get("session_hearbeat") != null) {
			p.remove("session_hearbeat");
		}

	}

	// @Override
	// public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
	// throws Exception {
	// logger.log(Level.WARNING,
	// "Server side Unexpected exception from downstream. {0}", e.getCause());
	//
	// e.getChannel().close();
	// }

	private static void send100Continue(MessageEvent e)
	{
		HttpResponse response = new DefaultHttpResponse(HTTP_1_1, CONTINUE);
		e.getChannel().write(response);
	}

	@Override
	public String getName()
	{
		return RFIDIchHandler.class.getName();
	}

	public void setMessageDispatcher(IMessageDispatcher messageDispatcher)
	{
		this.messageDispatcher = messageDispatcher;
	}

}
