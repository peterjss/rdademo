/*
 * @(#)MessageHelper.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/**
 * Class description goes here.
 * 
 * @version 1.00 2013-3-6
 * @author <a href="mailto:Peter@iaspec.com">Peter</a>
 */

public class MessageHelper
{
	public byte[] encodeToByte(MessageRequest request)
	{
		if (request == null) {
			return null;
		}

		ByteBuffer bb = ByteBuffer.allocate(25 + 38 + 36 + request.getMessageLength());

		// Message Head
		bb.putInt(request.getVersionNo());
		bb.putInt(request.getSequenceNo());
		bb.put(request.getCountinueFlag());
		bb.putLong(request.getTimestamp());

		// Message Block
		byte[] messageId = new byte[32];
		System.arraycopy(request.getMessageId().getBytes(), 0, messageId, 0, request.getMessageId().getBytes().length);
		bb.put(messageId);
		bb.putShort(request.getMessageType());
		bb.putShort(request.getMessagePriority());
		bb.putInt(request.getMessageLength());

		// Message Body
		byte[] deviceId = new byte[32];
		System.arraycopy(request.getDeviceId().getBytes(), 0, deviceId, 0, request.getDeviceId().getBytes().length);
		bb.put(deviceId);
		// bb.put(request.getDeviceId().getBytes());

		byte[] messageTime = new byte[4];
		System.arraycopy(request.getMessageTime().getBytes(), 0, messageTime, 0,
		        request.getMessageTime().getBytes().length);
		bb.put(messageTime);

		// bb.put(request.getMessageTime().getBytes());
		bb.put(request.getEpcList());

		return bb.array();
	}

	public MessageRequest decodeToRequest(byte[] data)
	{
		if (data == null) {
			return null;
		}

		MessageRequest request = new MessageRequest();

		ByteBuffer bb = ByteBuffer.wrap(data);
		request.setVersionNo(bb.getInt());
		request.setSequenceNo(bb.getInt());
		request.setCountinueFlag(bb.get());
		request.setTimestamp(bb.getLong());

		byte[] messageId = new byte[32];
		bb.get(messageId);

		request.setMessageId(new String(messageId));
		request.setMessageType(bb.getShort());
		request.setMessagePriority(bb.getShort());

		int messageLength = bb.getInt();
		request.setMessageLength(messageLength);

		byte[] deviceId = new byte[32];
		bb.get(deviceId);
		request.setDeviceId(new String(deviceId));

		byte[] messageTime = new byte[4];
		bb.get(messageTime);
		request.setMessageTime(new String(messageTime));

		byte[] epcList = new byte[messageLength];
		bb.get(epcList);
		request.setEpcList(epcList);

		return request;
	}

	// private byte[] int2ByteArray(int i)
	// {
	// byte[] result = new byte[4];
	// result[0] = (byte) ((i >> 24) & 0xFF);
	// result[1] = (byte) ((i >> 16) & 0xFF);
	// result[2] = (byte) ((i >> 8) & 0xFF);
	// result[3] = (byte) (i & 0xFF);
	// return result;
	// }
	//
	// private int byteArray2Int(byte[] b, int offset)
	// {
	// int value = 0;
	// for (int i = 0; i < 4; i++) {
	// int shift = (4 - 1 - i) * 8;
	// value += (b[i + offset] & 0x000000FF) << shift;
	// }
	// return value;
	// }

	public String generateMessageId()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("-epclist-ReaderID-");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String s = sdf.format(new Date());
		sb.append(s);
		UUID uuid = UUID.randomUUID();
		// Calendar c = Calendar.getInstance();
		// sb.append(c.get(Calendar.YEAR));
		// sb.append(c.get(Calendar.MONTH));
		// sb.append(c.get(Calendar.DAY_OF_MONTH));
		// sb.append(c.get(Calendar.HOUR_OF_DAY));
		// sb.append(c.get(Calendar.MINUTE));
		// sb.append(c.get(Calendar.SECOND));

		return uuid.toString().replace("-", "");

	}

	public static void main(String[] args)
	{
		Date d = new Date();
		d.getTime();

		MessageHelper mh = new MessageHelper();
		MessageRequest request = new MessageRequest();
		request.setVersionNo(198888);
		request.setSequenceNo(15);
		request.setCountinueFlag((byte) 1);
		request.setTimestamp(d.getTime());

		// request.setMessageId("-epclist-ReaderID-yyyyMMddhhmmss");
		request.setMessageId(mh.generateMessageId());

		request.setMessageType((short) 25);
		request.setMessagePriority((short) 10);

		request.setMessageLength(5);

		request.setDeviceId("1234567890_1234567890_1234567890");
		request.setMessageTime("2000");

		request.setEpcList("abcde".getBytes());
		// byte[] resault = mh.format2Byte(request);
		//
		// int a = mh.byteArray2Int(resault, 0);
		// int t ;
		// t=sizeof(short);
		// short, int ,long, short, double
		// Date d = new Date();
		// d.getTime();
		// Calendar c = Calendar.getInstance();
		// c.getTimeInMillis();

		byte[] a = mh.encodeToByte(request);
		MessageRequest request2 = mh.decodeToRequest(a);

		System.out.println(request.getEpcList().length);
	}
}
