/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl.bdb;


import com.iaspec.rda.foundation.message.IMessageQueue;
import com.iaspec.rda.foundation.message.MessageRequest;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public final class BdbMessageQueue implements IMessageQueue
{
	private static final long serialVersionUID = -7270898156685080944L;

	// private Queue<IMessageRequest> requestQueue;
	private BdbPersistenceStore<MessageRequest> bdbPersistenceStore;

	private boolean running = false;

	public BdbMessageQueue(BdbPersistenceStore<MessageRequest> bdbPersistenceStore)
	{
		this.bdbPersistenceStore = bdbPersistenceStore;
	}

	public BdbPersistenceStore<MessageRequest> getRequestQueue()
	{
		return bdbPersistenceStore;
	}

	/**
	 * Clear the queue.
	 */
	public void clear()
	{
		bdbPersistenceStore.clear();
		bdbPersistenceStore = null;
	}

	/**
	 * Get the length of queue.
	 * 
	 * @return
	 */
	public int size()
	{
		return bdbPersistenceStore.size();
	}

	/**
	 * Add the request to queue.
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(MessageRequest request)
	{

		return bdbPersistenceStore.offer(request);
	}

	/**
	 * Set the running state of queue.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running)
	{
		this.running = running;
	}

	/**
	 * Checking the queue is running or not.
	 * 
	 * @return
	 */
	public boolean isRunning()
	{
		return running;
	}
}
