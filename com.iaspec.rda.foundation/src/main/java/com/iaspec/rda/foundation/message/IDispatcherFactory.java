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
public interface IDispatcherFactory
{
	/**
	 * Create a dispatcher for sending message. sleepTime default is 200ms;
	 */
	public IMessageDispatcher createDispatcher();
}
