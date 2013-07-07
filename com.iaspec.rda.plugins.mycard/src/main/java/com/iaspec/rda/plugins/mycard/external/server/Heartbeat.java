/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.server;


import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.timeout.IdleState;
import org.jboss.netty.handler.timeout.IdleStateAwareChannelHandler;
import org.jboss.netty.handler.timeout.IdleStateEvent;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-11
 */
public class Heartbeat extends IdleStateAwareChannelHandler
{

    @Override
    public void channelIdle(ChannelHandlerContext ctx, IdleStateEvent e) throws Exception
    {
        super.channelIdle(ctx, e);

        if (e.getState() == IdleState.ALL_IDLE)
        {
            e.getChannel().close();
        }
    }
}
