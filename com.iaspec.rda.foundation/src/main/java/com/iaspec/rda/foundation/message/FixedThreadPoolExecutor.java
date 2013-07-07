/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-11
 */
public class FixedThreadPoolExecutor extends ThreadPoolExecutor
{

	public FixedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveSecond, String poolName)
	{
		super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(),
		        Executors.defaultThreadFactory());
	}

	public FixedThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveSecond)
	{
		super(corePoolSize, maximumPoolSize, keepAliveSecond, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
	}
}
