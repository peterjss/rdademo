/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.foundation.common.server;


import org.jboss.netty.channel.ChannelHandler;

public interface IServerHandler extends ChannelHandler
{
    public String getName();
}
