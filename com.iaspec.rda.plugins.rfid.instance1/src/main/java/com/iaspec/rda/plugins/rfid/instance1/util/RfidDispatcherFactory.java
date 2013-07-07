/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.instance1.util;


import com.iaspec.rda.foundation.message.ExecutorConfig;
import com.iaspec.rda.foundation.message.IMessageWorker;
import com.iaspec.rda.foundation.message.impl.memory.MemoryDispatcherFactory;


/**
 * Class description goes here.
 * 
 * @author <a href="mailto:Peter@iaspec.com">Peter</a>
 * @version 1.00 2013-1-29
 */

public class RfidDispatcherFactory extends MemoryDispatcherFactory
{

	@Override
	public IMessageWorker getMessageWorker()
	{
		RfidMessageWorker worker = new RfidMessageWorker();
		return worker;
	}

	@Override
	public ExecutorConfig getExecutorConfig()
	{
		ExecutorConfig config = new ExecutorConfig();
		config.setCorePoolSize(3);
		config.setMaximumPoolSize(10);
		config.setKeepAliveSecond(200);

		return config;
	}

}
