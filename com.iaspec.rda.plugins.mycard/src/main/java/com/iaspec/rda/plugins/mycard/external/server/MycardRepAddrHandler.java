/*
 * @(#)MycardRepAddrHandler.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.server;


import java.nio.ByteBuffer;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.plugins.mycard.external.protocol.PlatformRequest;
import com.iaspec.rda.plugins.mycard.external.protocol.PlatformResponse;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-13
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardRepAddrHandler extends SimpleChannelUpstreamHandler implements IProtocol
{
    private static final Logger logger = LoggerFactory.getLogger(MycardRepAddrHandler.class);
	
    @Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
    	logger.info("Receive reqAddr(3,1) from client ");
    	PlatformRequest request =new PlatformRequest();
    	if (e.getMessage() instanceof PlatformRequest)
    	{
    		 request = (PlatformRequest) e.getMessage();
    		 if(request !=null)
    	     {
    	    	 if(request.getSubRequest().getOption() == 0)
    	 		{
    	 			sendResponse(e);
    	 		}else
    	 		{
    	 			logger.debug("Access failed.");
    	 		}
    	     }
    	}
	}

	private void sendResponse(MessageEvent e)
	{
		logger.info("Send the 'reqAssignAddr_RES'(3,2) response to server.");
		PlatformResponse response =new PlatformResponse();
		byte[] bt = new byte[]{(byte) 0xFA,0x03,0x00,0x00,0x00,0x00,0x11,0x12,
				0x00,0x00,0x00,0x17,(byte) 0xFF,(byte) 0xFF,(byte) 0xFB}; 
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bt);
		response.setMessageHeader(byteBuffer.get());
		response.setProtocolNumber(byteBuffer.get());
		response.setPurposeAddress(byteBuffer.getInt());
		response.setSourceAddress(byteBuffer.getInt());
		response.setMessageLength(byteBuffer.getShort());
		byte[] sourceIDContent =new byte[]{0x10,0x47,0x50,0x52,0x53,0x43,0x6F,0x6D,0x6D,
				0x53,0x65,0x72,0x76,0x65,0x72,0x30,0x32};
		
		response.setSourceID(sourceIDContent);
		byte[] messageContent =new byte[]{0x02,0x00,0x11,0x12,0x00,0x00};
		response.setMessageContent(messageContent);
		response.setChecksum(byteBuffer.getShort());
		response.setMessageTail(byteBuffer.get());
		
        e.getChannel().write(response,e.getRemoteAddress());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
	{
		System.err.println("Server side Unexpected exception from downstream. msg: " + e.getCause().getMessage() +
		        e.getCause());
		e.getChannel().close();
	}
	
	@Override
    public String getName()
    {
	    return null;
    }

}
