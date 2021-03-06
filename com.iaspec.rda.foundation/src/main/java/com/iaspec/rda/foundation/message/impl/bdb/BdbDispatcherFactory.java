/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl.bdb;


import com.iaspec.rda.foundation.FoundationActivator;
import com.iaspec.rda.foundation.message.ExecutorConfig;
import com.iaspec.rda.foundation.message.FixedThreadPoolExecutor;
import com.iaspec.rda.foundation.message.IDispatcherFactory;
import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.IMessageWorker;
import com.iaspec.rda.foundation.message.MessageRequest;
import com.iaspec.rda.foundation.message.impl.MessageDispatcher;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-2-1
 */
public abstract class BdbDispatcherFactory implements IDispatcherFactory
{

	public static BdbMessageQueue BDB_MESSAGE_QUEUE(String sessionKey)
	{
		return new BdbMessageQueue(new BdbPersistenceStore<MessageRequest>(FoundationActivator.BDB_PATH, sessionKey,
		        MessageRequest.class));
	}

	/**
	 * Setting the Message Worker for message sending logic.
	 * 
	 * @return IMessageWorker, the instance of worker.
	 */
	public abstract IMessageWorker getMessageWorker();

	/**
	 * Setting the Executor Configuration, corePoolSize, maximumPoolSize and
	 * keepAliveSecond. Default 5, 10, 200.
	 * 
	 * @return ExecutorConfig, the object of configuration.
	 */
	public abstract ExecutorConfig getExecutorConfig();

	private IMessageDispatcher createDispatcher(Integer sleepTime)
	{
		MessageDispatcher md = new MessageDispatcher();
		IMessageWorker worker = this.getMessageWorker();
		md.init(worker);

		// MessageQueue messageQueue = new MessageQueue(new
		// ConcurrentLinkedQueue<IMessageRequest>());
		// md.addMessageQueue(key, messageQueue);

		Integer corePoolSize = 5;
		Integer maximumPoolSize = 10;
		Integer keepAliveSecond = 200;

		ExecutorConfig config = getExecutorConfig();

		if (config != null) {
			corePoolSize = config.getCorePoolSize() != null ? config.getCorePoolSize() : corePoolSize;
			maximumPoolSize = config.getMaximumPoolSize() != null ? config.getMaximumPoolSize() : maximumPoolSize;
			keepAliveSecond = config.getKeepAliveSecond() != null ? config.getKeepAliveSecond() : keepAliveSecond;
		}

		md.setMessageExecutor(new FixedThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveSecond));
		md.setSleepTime(sleepTime);

		return md;
	}

	@Override
	public IMessageDispatcher createDispatcher()
	{
		return createDispatcher(200);
	}

}
