/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.instance1;


import java.util.Map;

import javax.net.ssl.SSLEngine;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.handler.codec.compression.ZlibDecoder;
import org.jboss.netty.handler.codec.compression.ZlibEncoder;
import org.jboss.netty.handler.codec.compression.ZlibWrapper;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import org.jboss.netty.handler.codec.http.HttpChunkAggregator;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.ssl.SslHandler;
import org.jboss.netty.handler.stream.ChunkedWriteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.securechat.SecureChatSslContextFactory;
import com.iaspec.rda.foundation.common.server.IServerHandler;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.plugins.rfid.external.server.IchHandlerConfig;
import com.iaspec.rda.plugins.rfid.external.server.RFIDIchHandler;
import com.iaspec.rda.plugins.rfid.instance1.util.ServerConfig;


/**
 * Manipulates the current pipeline dynamically to switch protocols.
 */
public class RFIDIchInstanceHandler extends FrameDecoder implements IServerHandler
{
	private static final Logger logger = LoggerFactory.getLogger(RFIDIchInstanceHandler.class);

	private final String handlerName = this.getClass().getSimpleName();
	// static final ChannelGroup channels = new DefaultChannelGroup();

	private final boolean detectSsl;
	private final boolean detectGzip;
	private IMessageDispatcher messageDispatcher = RFIDInstanceActivator.messageDispatcher;

	public RFIDIchInstanceHandler()
	{
		this(true, true);
	}

	private RFIDIchInstanceHandler(boolean detectSsl, boolean detectGzip)
	{
		this.detectSsl = detectSsl;
		this.detectGzip = detectGzip;
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		// Will use the first two bytes to detect a protocol.
		if (buffer.readableBytes() < 5) {
			return null;
		}
		if (isSsl(buffer)) {
			logger.info("Detect SSL Handshake.");
			enableSsl(ctx);
		}
		else {
			final int magic1 = buffer.getUnsignedByte(buffer.readerIndex());
			final int magic2 = buffer.getUnsignedByte(buffer.readerIndex() + 1);
			if (isGzip(magic1, magic2)) {
				logger.info("Detect Gzip Handshake.");
				enableGzip(ctx);
			}
			else if (isHttp(magic1, magic2)) {
				logger.info("Detect HTTP Handshake.");
				switchToHttp(ctx);
			}
			else {
				// Unknown protocol; discard everything and close the
				// connection.
				buffer.skipBytes(buffer.readableBytes());
				ctx.getChannel().close();
				return null;
			}
		}
		// Forward the current read buffer as is to the new handlers.
		return buffer.readBytes(buffer.readableBytes());
	}

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		super.channelConnected(ctx, e);

		// RFIDIchServer.channels.add(e.getChannel());
		// QpidDispatcherFactory qf = new QpidDispatcherFactory();
		// UUID key = UUID.randomUUID();
		// messageDispatcher.addMessageQueue(ctx.getChannel().getId().toString(),
		// BdbDispatcherFactory.BDB_MESSAGE_QUEUE(ctx.getChannel().getId().toString()));
		// messageDispatcher.start();
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		// Unregister the channel from the global channel list
		// so the channel does not receive messages anymore.
		// RFIDIchServer.channels.remove(e.getChannel());

	}

	private boolean isSsl(ChannelBuffer buffer)
	{

		if (detectSsl) {
			return SslHandler.isEncrypted(buffer);
		}
		return false;
	}

	private boolean isGzip(int magic1, int magic2)
	{
		if (detectGzip) {
			return magic1 == 31 && magic2 == 139;
		}
		return false;
	}

	private static boolean isHttp(int magic1, int magic2)
	{
		return magic1 == 'G' && magic2 == 'E' || // GET
		        magic1 == 'P' && magic2 == 'O' || // POST
		        magic1 == 'P' && magic2 == 'U' || // PUT
		        magic1 == 'H' && magic2 == 'E' || // HEAD
		        magic1 == 'O' && magic2 == 'P' || // OPTIONS
		        magic1 == 'P' && magic2 == 'A' || // PATCH
		        magic1 == 'D' && magic2 == 'E' || // DELETE
		        magic1 == 'T' && magic2 == 'R' || // TRACE
		        magic1 == 'C' && magic2 == 'O'; // CONNECT
	}

	private void enableSsl(ChannelHandlerContext ctx)
	{
		ChannelPipeline p = ctx.getPipeline();

		SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
		engine.setUseClientMode(false);

		p.addLast("ssl", new SslHandler(engine));
		p.addLast("unificationA", new RFIDIchInstanceHandler(false, detectGzip));
		p.remove(this);
	}

	private void enableGzip(ChannelHandlerContext ctx)
	{
		ChannelPipeline p = ctx.getPipeline();
		p.addLast("gzipdeflater", new ZlibEncoder(ZlibWrapper.GZIP));
		p.addLast("gzipinflater", new ZlibDecoder(ZlibWrapper.GZIP));
		p.addLast("unificationB", new RFIDIchInstanceHandler(detectSsl, false));
		p.remove(this);
	}

	private void switchToHttp(ChannelHandlerContext ctx) throws Exception
	{
		ChannelPipeline p = ctx.getPipeline();
		Map<String, ChannelHandler> m = p.toMap();
		p.addLast("decoder", new HttpRequestDecoder());
		p.addLast("aggregator", new HttpChunkAggregator(65536));
		p.addLast("encoder", new HttpResponseEncoder());
		p.addLast("deflater", new HttpContentCompressor());
		p.addLast("chunkedWriter", new ChunkedWriteHandler());
		// p.addLast("timeout", new IdleStateHandler(new HashedWheelTimer(), 10,
		// 10, 0));
		// p.addLast("hearbeat", new Heartbeat());

		IchHandlerConfig config = new IchHandlerConfig();
		config.setSsl(!detectSsl);
		config.setServerId(ServerConfig.getInstance().server());
		config.setSessionTimeout(ServerConfig.getInstance().sessionTimeout());
		logger.debug("Session timeout : {}", config.getSessionTimeout());
		RFIDIchHandler handler = null;
		try {
			handler = new RFIDIchHandler(config);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		handler.setMessageDispatcher(messageDispatcher);

		p.addLast("handler", handler);
		// p.addLast("adhandler", new RFIDIchADHandler());

		p.remove(this);

	}

	@Override
	public String getName()
	{
		return handlerName;
	}

}
