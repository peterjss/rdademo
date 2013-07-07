/*
 * @(#)MycardIchHandler.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.external.server;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.jboss.netty.handler.timeout.IdleStateHandler;
import org.jboss.netty.util.HashedWheelTimer;

import com.iaspec.rda.foundation.common.protocol.IProtocol;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.plugins.mycard.entity.Session;
import com.iaspec.rda.plugins.mycard.external.dao.impl.SessionMapDao;
import com.iaspec.rda.plugins.mycard.external.protocol.GPSRequest;
import com.iaspec.rda.plugins.mycard.external.protocol.PlatformRequest;
import com.iaspec.rda.plugins.mycard.util.ExceptionMessages;
import com.iaspec.rda.plugins.mycard.util.MessageUtil;
import com.iaspec.rda.plugins.mycard.util.MycardException;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardIchHandler extends SimpleChannelUpstreamHandler implements IProtocol
{

    private static final Logger logger = Logger.getLogger(MycardIchHandler.class.getName());

    private String serverId;
    private int sessionTimeout = 60;
    private IchHandlerConfig config;
    private String sessionId;
    private IMessageDispatcher messageDispatcher;
	
    private SessionMapDao sessionMapDao = SessionMapDao.getInstance();
    private byte expectProtocolNum;
    private byte expectGpsProtocolNum =0x05;
    

    public MycardIchHandler(IchHandlerConfig config)
    {
        this.serverId = config.getServerId();
        this.sessionTimeout = config.getSessionTimeout();
        this.config = config;
    }
    

    @Override
    public void messageReceived(final ChannelHandlerContext ctx, MessageEvent e) throws Exception
    {
    	PlatformRequest request =new PlatformRequest();
    	if (e.getMessage() instanceof PlatformRequest)
    	{
    		 request = (PlatformRequest) e.getMessage();
    	}
    	if(request ==null)
    	{
    		 throw new RuntimeException("Access failed.");
    	}
    	ChannelPipeline p = ctx.getPipeline();
    	if(true)
    	{
        	if(request.getProtocolNumber() ==3)
        	{
        		if(request.getSubRequest().getProtocolNumber() ==1)
        		{
        			clearPipeline(p);
        			if (sessionId != null)
                    {
                        Session session = sessionMapDao.getCurrentSession(sessionId);
                        if (session != null)
                        {
                            sessionMapDao.remove(sessionId);
                        }
                    }
                    Session session = new Session();
                    session.setServerId(serverId);
                    session.setChallengeValue(Session.generateChallenge());
                    sessionId = sessionMapDao.createSession(session);
        			
        			p.addLast("reqAddrhandler", new MycardRepAddrHandler());
        			ctx.sendUpstream(e);
                    expectProtocolNum = 0x03;
                    expectGpsProtocolNum =0x05;
        		}
        		if(sessionId ==null)
    			{
    				throw new MycardException(ExceptionMessages.EXCEPTION_INVALID_SESSION);
    			}else
    			{
    	    		if(request.getSubRequest().getProtocolNumber() ==expectProtocolNum)
    	    		{
    	    			if (p.get("reqAddrhandler") != null) {
    	    				p.remove("reqAddrhandler");
    	    			}
    	    			
    	    			if (p.get("reqAssignAddrhandler") != null) 
    	    			{
    	    				p.remove("reqAssignAddrhandler");
    	    				if (p.get("reqLoginhandler") == null) {
    	        				p.addLast("reqLoginhandler", new MycardReqLoginHandler(sessionId));
    	    				}
    	    			}else
    	    			{
    	    				p.addLast("reqAssignAddrhandler", new MycardReAssignAddrHandler());
    	   				    expectProtocolNum = 0x05;
    	    			}
    	    			
    	    			ctx.sendUpstream(e);
    	    		}
    			}
        	}
        	
        	if(request.getProtocolNumber() ==6)
        	{
        		GPSRequest gps=new GPSRequest();
        		 gps =MessageUtil.decodeMessage(request.getMessageContent());
        		 if(request.getSubRequest().getProtocolNumber() ==2 && gps.getProtocolNumber() ==expectGpsProtocolNum)
        		 {
        			 clearDataTranPipeline(p);
        			if (p.get("initReporthandler") != null) 
        			{
        				p.remove("initReporthandler");
        				if (p.get("sendGPShandler") != null) 
            			{
        				   p.remove("sendGPShandler");
            			}
            				MycardSendGPSDataHandler gpsHandler = new MycardSendGPSDataHandler(config,sessionId);
            				gpsHandler.setMessageDispatcher(messageDispatcher);
            				p.addLast("sendGPShandler", gpsHandler);
            				p.addBefore("sendGPShandler", "session_timeout", new IdleStateHandler(new HashedWheelTimer(), 0, 0,
   	                                sessionTimeout));
   	                        p.addBefore("sendGPShandler", "session_hearbeat", new Heartbeat());
        			}else
        			{
        				MycardInitReportHandler reportHandler = new MycardInitReportHandler(sessionId);
        				p.addLast("initReporthandler", reportHandler);
        				expectGpsProtocolNum =0x39;
        			}
        			ctx.sendUpstream(e);
        		}
        	}
    	}/*else
    	{
    		throw new MycardException(ExceptionMessages.EXCEPTION_INVALID_DEVICE_OR_CONNECTION);
    	}*/

    }
    
	private void clearPipeline(ChannelPipeline p)
	{
		if (p.get("reqAddrhandler") != null) {
			p.remove("reqAddrhandler");
		}
		if (p.get("reqAssignAddrhandler") != null) {
			p.remove("reqAssignAddrhandler");
		}
		if (p.get("reqLoginhandler") != null) {
			p.remove("reqLoginhandler");
		}
		if (p.get("initReporthandler") != null) {
			p.remove("initReporthandler");
		}
		if (p.get("sendGPShandler") != null) {
			p.remove("sendGPShandler");
		}
		 if (p.get("session_timeout") != null)
        {
            p.remove("session_timeout");
        }
        if (p.get("session_hearbeat") != null)
        {
            p.remove("session_hearbeat");
        }
        if (p.get("loghandler") != null)
        {
            p.remove("loghandler");
        }
	}

	private void clearDataTranPipeline(ChannelPipeline p)
	{
		if (p.get("reqAddrhandler") != null) {
			p.remove("reqAddrhandler");
		}
		if (p.get("reqAssignAddrhandler") != null) {
			p.remove("reqAssignAddrhandler");
		}
		if (p.get("reqLoginhandler") != null) {
			p.remove("reqLoginhandler");
		}
		 if (p.get("session_timeout") != null)
        {
            p.remove("session_timeout");
        }
        if (p.get("session_hearbeat") != null)
        {
            p.remove("session_hearbeat");
        }
        if (p.get("loghandler") != null)
        {
            p.remove("loghandler");
        }
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception
    {
        logger.log(Level.WARNING, "Server side Unexpected exception from downstream.", e.getCause());
        e.getChannel().close();
    }
	
    @Override
    public String getName()
    {
        return null;
    }

	public void setMessageDispatcher(IMessageDispatcher messageDispatcher) {
		this.messageDispatcher = messageDispatcher;
	}
}