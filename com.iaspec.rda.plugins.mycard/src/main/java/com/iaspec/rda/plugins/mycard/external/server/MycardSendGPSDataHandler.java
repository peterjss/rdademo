/*
 * @(#)MycardSendGPSDataHandler.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.server;

import java.nio.ByteBuffer;
import java.util.Date;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.MessageRequest;
import com.iaspec.rda.plugins.mycard.entity.Session;
import com.iaspec.rda.plugins.mycard.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.mycard.external.protocol.GPSRequest;
import com.iaspec.rda.plugins.mycard.external.protocol.PlatformRequest;
import com.iaspec.rda.plugins.mycard.external.protocol.PlatformResponse;
import com.iaspec.rda.plugins.mycard.util.ExceptionMessages;
import com.iaspec.rda.plugins.mycard.util.MessageUtil;
import com.iaspec.rda.plugins.mycard.util.MycardException;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-13
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardSendGPSDataHandler  extends SimpleChannelUpstreamHandler implements IProtocol
{
	private static final Logger logger =LoggerFactory.getLogger(MycardSendGPSDataHandler.class);
    private String serverId;
    private IMessageDispatcher messageDispatcher;
    private String sessionId;
    
    public MycardSendGPSDataHandler(IchHandlerConfig config,String sessionId)
    {
        this.serverId = config.getServerId();
        this.sessionId = sessionId;
    }
    
	@Override
	public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
    	PlatformRequest request =new PlatformRequest();
    	if (e.getMessage() instanceof PlatformRequest)
    	{
    		 request = (PlatformRequest) e.getMessage();
    		 
    		 Session session = SessionMapDao.getInstance().getCurrentSession(sessionId);
    		 if (session == null || !sessionId.equalsIgnoreCase(session.getId()) || !session.isAccessed()
    			        || !session.isAlive()) {
    				throw new MycardException(ExceptionMessages.EXCEPTION_INVALID_SESSION);
    		 }
    		 
    		 GPSRequest gps =MessageUtil.decodeMessage(request.getMessageContent());
    		 System.out.println("Receive initReport(6,2,57) from client , protocol: " + request.getProtocolNumber() + "subProtocl: " +request.getMessageContent()[0]);
    		 MessageRequest message = new MessageRequest();
			 
    		 message.setSessionId(serverId);
			 message.setVersionNo(1);
			 message.setSequenceNo(1);
			 message.setCountinueFlag((byte) 1);
			 message.setTimestamp(new Date().getTime());

			 message.setMessageId(MessageRequest.generateMessageId());

			 message.setMessageType(MessageRequest.MESSAGETYPE_NORMAL);
			 message.setMessagePriority(MessageRequest.MESSAGEPRIORITY_DEFAULT);
			 message.setMessageLength(gps.getMessageLength());
			 message.setDeviceId(new String(request.getSourceID()));
			 message.setMessageTime("2012");
			 message.setEpcList(gps.getMessageContent());
			 messageDispatcher.addMessage(message);
    	}
		sendResponse(e);
	}

	private void sendResponse(MessageEvent e)
	{
		logger.info("Send the 'link test ok'(2,2) response to client.");
		PlatformResponse response =new PlatformResponse();
		byte[] bt = new byte[]{(byte) 0xFA,0x02,0x11,0x12,0x00,0x11,0x12,0x07,
				(byte) 0xCD,0x00,0x00,0x13,(byte) 0xFF,(byte) 0xFF,(byte) 0xFB}; 
		
		ByteBuffer byteBuffer = ByteBuffer.wrap(bt);
		response.setMessageHeader(byteBuffer.get());
		response.setProtocolNumber(byteBuffer.get());
		response.setPurposeAddress(byteBuffer.getInt());
		response.setSourceAddress(byteBuffer.getInt());
		response.setMessageLength(byteBuffer.getShort());
		byte[] sourceIDContent =new byte[]{0x10,0x47,0x50,0x52,0x53,0x43,0x6F,0x6D,0x6D,
				0x53,0x65,0x72,0x76,0x65,0x72,0x30,0x32};
		
		response.setSourceID(sourceIDContent);
		byte[] messageContent =new byte[]{0x02,0x00};
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

	/**
	 * @param messageDispatcher the messageDispatcher to set
	 */
	public void setMessageDispatcher(IMessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}

}