/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.instance1;


import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.impl.bdb.BdbDispatcherFactory;
import com.iaspec.rda.plugins.rfid.instance1.util.RfidBdbDispatcherFactory;
import com.iaspec.rda.plugins.rfid.instance1.util.ServerConfig;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-14
 */
public class RFIDInstanceActivator implements BundleActivator
{
	public static BundleContext context;
	public static IMessageDispatcher messageDispatcher;

	public void start(BundleContext context) throws Exception
	{
		RFIDInstanceActivator.context = context;
		ServerConfig serverConfig = ServerConfig.getInstance();

		messageDispatcher = new RfidBdbDispatcherFactory().createDispatcher();
		messageDispatcher.addMessageQueue(serverConfig.server(),
		        BdbDispatcherFactory.BDB_MESSAGE_QUEUE(serverConfig.server()));
		messageDispatcher.start();
	}

	public void stop(BundleContext context) throws Exception
	{
		ServerConfig serverConfig = ServerConfig.getInstance();
		messageDispatcher.removeMessageQueue(serverConfig.server());
		messageDispatcher.stop();
	}

	public static BundleContext getContext()
	{
		return context;
	}

}
