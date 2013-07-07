/*
 * @(#)PlatformProtocolRequst.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.protocol;

import java.util.Arrays;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-6
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class PlatformRequest 
{
	private byte messageHeader;//Message Header Value(1byte). 
	private byte protocolNumber;//Protocol Number Value(1byte). 
	private int purposeAddress;//Purpose Address Value(4byte). 
	private int sourceAddress;//Source Address Value(4byte). 
	private short messageLength;//Message Length (2byte). include length of sourceID and messageContent.
	private byte[] sourceID;//Source ID Value(0-256byte). 
	private byte[] messageContent;//MessageContent Value(0-1500byte). 
	private short checksum;//Checksum Value(2byte). 
	private byte messageTail;//Message Tail Value(1byte). 
	
	private PlatformSubRequest subRequest;
	
	public byte getMessageHeader() {
		return messageHeader;
	}
	
	public void setMessageHeader(byte messageHeader) {
		this.messageHeader = messageHeader;
	}
	
	public byte getProtocolNumber() {
		return protocolNumber;
	}
	
	public void setProtocolNumber(byte protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	
	public int getPurposeAddress() {
		return purposeAddress;
	}
	
	public void setPurposeAddress(int purposeAddress) {
		this.purposeAddress = purposeAddress;
	}
	
	public int getSourceAddress() {
		return sourceAddress;
	}
	
	public void setSourceAddress(int sourceAddress) {
		this.sourceAddress = sourceAddress;
	}
	
	public short getMessageLength() {
		return messageLength;
	}
	
	public void setMessageLength(short messageLength) {
		this.messageLength = messageLength;
	}
	
	public byte[] getSourceID() {
		return sourceID;
	}
	
	public void setSourceID(byte[] sourceID) {
		this.sourceID = sourceID;
	}
	
	public byte[] getMessageContent() {
		return messageContent;
	}
	
	public void setMessageContent(byte[] messageContent) {
		this.messageContent = messageContent;
	}
	
	public short getChecksum() {
		return checksum;
	}
	
	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}
	
	public byte getMessageTail() {
		return messageTail;
	}
	
	public void setMessageTail(byte messageTail) {
		this.messageTail = messageTail;
	}

	/**
	 * @return the subRequest
	 */
	public PlatformSubRequest getSubRequest() {
		return subRequest;
	}

	/**
	 * @param subRequest the subRequest to set
	 */
	public void setSubRequest(PlatformSubRequest subRequest) {
		this.subRequest = subRequest;
	}

	@Override
	public String toString() {
		return "PlatformProtocolRequst [messageHeader=" + messageHeader
				+ ", protocolNumber=" + protocolNumber + ", purposeAddress="
				+ purposeAddress + ", sourceAddress=" + sourceAddress
				+ ", messageLength=" + messageLength + ", sourceID="
				+ Arrays.toString(sourceID) + ", messageContent="
				+ Arrays.toString(messageContent) + ", checksum=" + checksum
				+ ", messageTail=" + messageTail + "]";
	}
	
}
