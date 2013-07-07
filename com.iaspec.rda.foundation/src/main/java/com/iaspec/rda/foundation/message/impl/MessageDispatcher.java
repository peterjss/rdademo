/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.IMessageQueue;
import com.iaspec.rda.foundation.message.IMessageWorker;
import com.iaspec.rda.foundation.message.MessageRequest;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-25
 */
public class MessageDispatcher implements IMessageDispatcher
{

	private Logger logger = LoggerFactory.getLogger(MessageDispatcher.class);

	private boolean running;
	private long sleepTime = 200;
	private Executor messageExecutor;
	private Map<String, IMessageQueue> sessionMsgQ;

	private IMessageWorker worker;

	public void init(IMessageWorker worker)
	{
		this.worker = worker;
		logger.debug("Set the Executor Worker as {}", worker.getClass());

		if (!running) {
			running = true;
			sessionMsgQ = new ConcurrentHashMap<String, IMessageQueue>();
		}
	}

	@Override
	public void start()
	{
		new Thread(this).start();
	}

	@Override
	public void stop()
	{
		running = false;
	}

	@Override
	public void run()
	{
		while (running) {
			Set<String> keySet = sessionMsgQ.keySet();
			for (String key : keySet) {
				IMessageQueue messageQueue = sessionMsgQ.get(key);
				if (messageQueue == null || messageQueue.size() <= 0 || messageQueue.isRunning()) {
					continue;
				}
				IMessageWorker w = null;
				try {
					w = (IMessageWorker) worker.getInstance();
					w.setMessageQueue(messageQueue);
				}
				catch (Exception ex) {
					// logger.error(ex.getMessage(), ex);
				}
				this.messageExecutor.execute(w);
			}
			try {
				Thread.sleep(sleepTime);
			}
			catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public void addMessageQueue(String key, IMessageQueue messageQueue)
	{
		sessionMsgQ.put(key, messageQueue);
	}

	/**
	 * 为当前session添加消息
	 * 
	 * @param request
	 * @return
	 */
	public boolean addMessage(MessageRequest request)
	{
		boolean added = false;
		// int channelId= request.getChannel().getId();
		IMessageQueue messageQueue = sessionMsgQ.get(request.getSessionId());
		if (messageQueue == null) {
			// request.getChannel().close();
			logger.error("", new IllegalStateException());
		}
		else {
			added = messageQueue.add(request);
		}
		return added;
	}

	/**
	 * @param key
	 */
	@Override
	public void removeMessageQueue(String key)
	{
		IMessageQueue queue = sessionMsgQ.remove(key);
		if (queue != null) {
			queue.clear();
		}
	}

	public void setSleepTime(long sleepTime)
	{
		this.sleepTime = sleepTime;
	}

	public void setMessageExecutor(Executor messageExecutor)
	{
		this.messageExecutor = messageExecutor;
	}

}
