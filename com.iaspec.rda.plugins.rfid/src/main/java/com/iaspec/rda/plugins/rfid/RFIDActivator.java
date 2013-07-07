/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid;


import com.iaspec.rda.plugins.rfid.entity.Session;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-14
 */
public class RFIDActivator implements BundleActivator
{
    public static BundleContext context;
    // public static String sessionDBPath = "DB-INF";
    // public static BdbPersistenceStore<Session> sessionDB;
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
