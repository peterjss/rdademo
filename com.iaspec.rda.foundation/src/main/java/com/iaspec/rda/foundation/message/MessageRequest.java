

package com.iaspec.rda.foundation.message;


import java.util.UUID;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-6
 */
public class MessageRequest implements IMessageRequest
{

	private static final long serialVersionUID = 3674636907104206923L;

	public static final byte COUNTINUE_YES = 1;
	public static final byte COUNTINUE_NO = 0;

	public static final short MESSAGETYPE_NORMAL = 1;
	public static final short MESSAGEPRIORITY_DEFAULT = 1;

	private String sessionId;

	// Message Head (25 bytes)
	private int versionNo; // 4 bytes
	private int sequenceNo; // 4 bytes
	private byte countinueFlag;// 1 byte
	private long timestamp; // 16 bytes

	// Message Type Block (38 bytes)
	private String messageId; // 32 bytes

	private short messageType; // 2 bytes
	private short messagePriority; // 2 bytes
	private int messageLength; // 4 bytes

	// Message Body (36 + x bytes)
	private String deviceId; // 32 bytes
	private String messageTime; // 4 bytes
	private byte[] epcList; // variable

	public static String generateMessageId()
	{
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}

	public String getSessionId()
	{
		return sessionId;
	}

	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	public int getVersionNo()
	{
		return versionNo;
	}

	public void setVersionNo(int versionNo)
	{
		this.versionNo = versionNo;
	}

	public int getSequenceNo()
	{
		return sequenceNo;
	}

	public void setSequenceNo(int sequenceNo)
	{
		this.sequenceNo = sequenceNo;
	}

	public byte getCountinueFlag()
	{
		return countinueFlag;
	}

	public void setCountinueFlag(byte countinueFlag)
	{
		this.countinueFlag = countinueFlag;
	}

	public long getTimestamp()
	{
		return timestamp;
	}

	public void setTimestamp(long timestamp)
	{
		this.timestamp = timestamp;
	}

	public String getMessageId()
	{
		return messageId;
	}

	public void setMessageId(String messageId)
	{
		this.messageId = messageId;
	}

	public short getMessageType()
	{
		return messageType;
	}

	public void setMessageType(short messageType)
	{
		this.messageType = messageType;
	}

	public short getMessagePriority()
	{
		return messagePriority;
	}

	public void setMessagePriority(short messagePriority)
	{
		this.messagePriority = messagePriority;
	}

	public int getMessageLength()
	{
		return messageLength;
	}

	public void setMessageLength(int messageLength)
	{
		this.messageLength = messageLength;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getMessageTime()
	{
		return messageTime;
	}

	public void setMessageTime(String messageTime)
	{
		this.messageTime = messageTime;
	}

	public byte[] getEpcList()
	{
		return epcList;
	}

	public void setEpcList(byte[] epcList)
	{
		this.epcList = epcList;
	}
}
