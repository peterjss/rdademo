/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.foundation.common.protocol;


import org.jboss.netty.channel.ChannelHandler;

public interface IProtocol extends ChannelHandler
{

    public String getName();
}
