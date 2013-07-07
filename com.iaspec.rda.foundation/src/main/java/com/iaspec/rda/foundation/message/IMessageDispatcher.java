/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-25
 */
public interface IMessageDispatcher extends Runnable
{
	public boolean addMessage(MessageRequest request);

	public void addMessageQueue(String key, IMessageQueue messageQueue);

	public void removeMessageQueue(String key);

	public void start();

	public void stop();
}
