/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message.impl;


import com.iaspec.rda.foundation.message.IMessageQueue;
import com.iaspec.rda.foundation.message.IMessageRequest;
import com.iaspec.rda.foundation.message.IMessageWorker;
import com.iaspec.rda.foundation.message.MessageRequest;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public abstract class AbstractMessageWorker implements IMessageWorker
{
	private IMessageQueue messageQueue;
	protected MessageRequest request;

	@Override
	public void setMessageQueue(IMessageQueue messageQueue)
	{
		messageQueue.setRunning(true);
		request = messageQueue.getRequestQueue().peek();
		this.messageQueue = messageQueue;
	}

	@Override
	public IMessageQueue getMessageQueue()
	{
		return messageQueue;
	}

	protected abstract void sendMessage() throws Exception;

	// public abstract Object clone() throws CloneNotSupportedException;

	public void run()
	{
		try {
			sendMessage();
			messageQueue.getRequestQueue().poll();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			messageQueue.setRunning(false);
			System.out.println(messageQueue.size());
		}
	}

	public IMessageRequest getRequest()
	{
		return request;
	}

	// @Override
	// public Object clones(IMessageWorker obj) throws Exception
	// {
	// ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
	// ObjectOutputStream out;
	// out = new ObjectOutputStream(byteOut);
	// out.writeObject(obj);
	//
	// ByteArrayInputStream byteIn = new
	// ByteArrayInputStream(byteOut.toByteArray());
	// ObjectInputStream in = new ObjectInputStream(byteIn);
	//
	// return in.readObject();
	// }

}
