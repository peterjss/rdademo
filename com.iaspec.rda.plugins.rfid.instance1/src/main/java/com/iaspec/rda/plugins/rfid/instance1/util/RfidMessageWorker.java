/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.instance1.util;


import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.apache.qpid.client.AMQAnyDestination;
import org.apache.qpid.client.AMQConnection;

import com.iaspec.rda.foundation.message.MessageHelper;
import com.iaspec.rda.foundation.message.impl.AbstractMessageWorker;


/**
 * Class description goes here.
 * 
 * @author <a href="mailto:Peter@iaspec.com">Peter</a>
 * @version 1.00 2013-1-28
 */

public class RfidMessageWorker extends AbstractMessageWorker
{
	private MapMessage mapMessage;

	@Override
	public Object getInstance()
	{
		return new RfidMessageWorker();
	}

	@Override
	public void sendMessage() throws Exception
	{
		String messageId = request.getSessionId();

		MessageHelper mh = new MessageHelper();
		byte[] msg = mh.encodeToByte(request);

		Connection connection = new AMQConnection("amqp://guest:guest@test/?brokerlist='tcp://localhost:5672'");

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination queue = new AMQAnyDestination("ADDR:rfid_message_queue; {create: always}");
		MessageProducer producer = session.createProducer(queue);

		mapMessage = session.createMapMessage();

		mapMessage.setLong("timestamp", request.getTimestamp());
		mapMessage.setBytes("msg", msg);

		producer.send(mapMessage);
		connection.close();
		request = null;
		System.out.println("messageId : " + messageId);
	}

	public void setMapMessage(MapMessage mapMessage)
	{
		this.mapMessage = mapMessage;
	}

}
