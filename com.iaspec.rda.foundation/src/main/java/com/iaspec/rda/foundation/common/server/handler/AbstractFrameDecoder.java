/*
 * @(#)AbstractFrameDecoder.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.common.server.handler;


import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.iaspec.rda.foundation.common.server.IServerHandler;


/**
 * Class description goes here.
 * 
 * @author <a href="mailto:Peter@iaspec.com">Peter</a>
 * @version 1.00 2013-3-20
 */

public abstract class AbstractFrameDecoder extends FrameDecoder implements IServerHandler
{

	protected abstract Object decodes(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer)
	        throws Exception;

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		return decodes(ctx, channel, buffer);
	}
}
