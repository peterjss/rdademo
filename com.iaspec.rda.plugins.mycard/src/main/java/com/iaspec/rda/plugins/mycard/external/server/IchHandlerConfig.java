/*
 * @(#)IchHandlerConfig.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.server;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class IchHandlerConfig
{
    public String serverId;
    public boolean ssl;
    public int sessionTimeout;

    public String getServerId()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }

    public boolean isSsl()
    {
        return ssl;
    }

    public void setSsl(boolean ssl)
    {
        this.ssl = ssl;
    }

    public int getSessionTimeout()
    {
        return sessionTimeout;
    }

    public void setSessionTimeout(int sessionTimeout)
    {
        this.sessionTimeout = sessionTimeout;
    }
}
