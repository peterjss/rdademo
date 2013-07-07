/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public interface IMessageWorker extends Runnable, Cloneable
{

	public void setMessageQueue(IMessageQueue messageQueue);

	public IMessageQueue getMessageQueue();

	public Object getInstance();

}