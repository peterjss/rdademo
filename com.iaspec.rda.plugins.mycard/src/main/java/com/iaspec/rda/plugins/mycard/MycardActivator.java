/*
 * @(#)MycardActivator.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iaspec.rda.plugins.mycard.entity.Session;



/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardActivator implements BundleActivator
{
    public static BundleContext context;
    public static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    public void start(BundleContext context) throws Exception
    {
        this.context = context;
        this.sessionMap = new ConcurrentHashMap<String, Session>();
    }

    public void stop(BundleContext context) throws Exception
    {
    }

    public static BundleContext getContext()
    {
        return context;
    }
}

