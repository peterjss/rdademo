/*
 * @(#)MycardDecoder.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.external.protocol;


import java.nio.ByteBuffer;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

import com.iaspec.rda.plugins.mycard.external.protocol.PlatformRequest;



/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-7
 * @author <a href="mailto:Hoo@iaspec.com">Hoo</a>
 */

public class MycardDecoder extends FrameDecoder
{
	/* (non-Javadoc)
	 * @see org.jboss.netty.handler.codec.frame.FrameDecoder#decode(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.Channel, org.jboss.netty.buffer.ChannelBuffer)
	 */
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) throws Exception
	{
		PlatformRequest request =new PlatformRequest();
		PlatformSubRequest subRequest =new PlatformSubRequest();
		if (buffer.readableBytes() < 10)
        {
            return null;
        }
        buffer.markReaderIndex();
     	
        request.setMessageHeader(buffer.readByte());
        request.setProtocolNumber(buffer.readByte());
        
        request.setPurposeAddress(buffer.readInt());
        request.setSourceAddress(buffer.readInt());
        
        request.setMessageLength(buffer.readShort());
        short totalLength = request.getMessageLength();
        short sourceIdLength = buffer.readByte();
        
        if(sourceIdLength >256)
        {
        	return null;
        }else
        {
            byte[] tempSourceId = new byte[sourceIdLength];
            buffer.readBytes(tempSourceId);
            request.setSourceID(tempSourceId);
        }
        
        byte[] tempContent = new byte[totalLength -sourceIdLength-1];
        buffer.readBytes(tempContent);
        request.setMessageContent(tempContent);
        
        ByteBuffer subByteBuffer = ByteBuffer.wrap(request.getMessageContent());
        subRequest.setProtocolNumber(subByteBuffer.get());
        subRequest.setOption(subByteBuffer.get());
        
        if(subRequest.getProtocolNumber() == 3)
        {
        	subRequest.setMethod(subByteBuffer.get());
        	subRequest.setType(subByteBuffer.get());
        	subRequest.setAddr(subByteBuffer.getInt());
        }
        
        if(subRequest.getProtocolNumber() == 5)
        {
        	byte[] ID = new byte[totalLength-sourceIdLength-3];
        	subByteBuffer.get(ID);
        	subRequest.setID(new String(ID));
        }
        
        request.setChecksum(buffer.readShort());
        request.setMessageTail(buffer.readByte());
        request.setSubRequest(subRequest);
		return request;
	}
	
}
