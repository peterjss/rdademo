/*
 * @(#)MycardEncoder.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.external.protocol;

import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.oneone.OneToOneEncoder;

import com.iaspec.rda.plugins.mycard.external.protocol.PlatformResponse;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-8
 * @author <a href="mailto:Hoo@iaspec.com">Hoo</a>
 */

public class MycardEncoder extends OneToOneEncoder
{

	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.oneone.OneToOneEncoder#encode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, java.lang.Object)
	 */
	@Override
	protected Object encode(ChannelHandlerContext ctx, Channel channel, Object msg) throws Exception
	{
		if(!(msg instanceof PlatformResponse))
		{
			return msg;
		}
		PlatformResponse response = (PlatformResponse) msg;
		
		ByteBuffer byteBuffer = ByteBuffer.allocate(15+response.getMessageLength());
		byteBuffer.put(response.getMessageHeader());
		byteBuffer.put(response.getProtocolNumber());
		byteBuffer.putInt(response.getPurposeAddress());
		byteBuffer.putInt(response.getSourceAddress());
		byteBuffer.putShort(response.getMessageLength());
		byteBuffer.put(response.getSourceID());
		byteBuffer.put(response.getMessageContent());
		byteBuffer.putShort(response.getChecksum());
		byteBuffer.put(response.getMessageTail());
		
		ChannelBuffer totalBuffer = ChannelBuffers.copiedBuffer(byteBuffer.array());
		byteBuffer.flip();
		 
		return totalBuffer;
	}

}
