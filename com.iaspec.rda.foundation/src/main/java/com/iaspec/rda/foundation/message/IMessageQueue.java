/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


import java.io.Serializable;
import java.util.Queue;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-2-1
 */
public interface IMessageQueue extends Serializable
{

	public Queue<MessageRequest> getRequestQueue();

	/**
	 * Clear the queue.
	 */
	public void clear();

	/**
	 * Get the length of queue.
	 * 
	 * @return
	 */
	public int size();

	/**
	 * Add the request to queue.
	 * 
	 * @param request
	 * @return
	 */
	public boolean add(MessageRequest request);

	/**
	 * Set the running state of queue.
	 * 
	 * @param running
	 */
	public void setRunning(boolean running);

	/**
	 * Checking the queue is running or not.
	 * 
	 * @return
	 */
	public boolean isRunning();
}
