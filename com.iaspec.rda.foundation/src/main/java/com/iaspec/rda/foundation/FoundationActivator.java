/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iaspec.rda.foundation.log.ILogProxy;
import com.iaspec.rda.foundation.log.Log4jProxyFactory;


/**
 * Class description goes here.
 * 
 * @author <a href="mailto:Peter@iaspec.com">Peter</a>
 * @version 1.00 2013-1-18
 */

public class FoundationActivator implements BundleActivator
{
	public static BundleContext context;
	private ILogProxy logProxy = Log4jProxyFactory.getInstance();
	public static String BDB_PATH;
	
	public void start(BundleContext context) throws Exception
	{
		this.context = context;
		logProxy.createProxy();
		BDB_PATH = "./db/";

	}

	public void stop(BundleContext context) throws Exception
	{
		logProxy.shutdown();
	}

	public static BundleContext getContext()
	{
		return context;
	}
}
