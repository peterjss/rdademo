/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl.memory;


import java.util.Queue;

import com.iaspec.rda.foundation.message.IMessageQueue;
import com.iaspec.rda.foundation.message.MessageRequest;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public final class MemoryMessageQueue implements IMessageQueue
{
	private Queue<MessageRequest> requestQueue;
	private boolean running = false;

	public MemoryMessageQueue(Queue<MessageRequest> requestQueue)
	{
		this.requestQueue = requestQueue;
	}

	public Queue<MessageRequest> getRequestQueue()
	{
		return requestQueue;
	}

	/**
	 * Clear the queue.
	 */
	public void clear()
	{
		requestQueue.clear();
		requestQueue = null;
	}

	/**
	 * Get the length of queue.
	 * 
	 * @return
	 */
	public int size()
	{
		return requestQueue != null ? requestQueue.size() : 0;
	}

	/**
	 * Add the request to queue.
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(MessageRequest request)
	{
		return this.requestQueue.add(request);
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
