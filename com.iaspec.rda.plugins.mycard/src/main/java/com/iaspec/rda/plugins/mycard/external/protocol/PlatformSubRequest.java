/*
 * @(#)SubProtocol.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.protocol;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-6
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class PlatformSubRequest 
{

	private byte  protocolNumber;
	private byte  option;
	private byte[]  messageContent;
	
	private int addr;//addr Value(4byte). 
	private byte method;//method Value(1byte). 
	private byte type;//type Value(1byte). 
	
	private String ID;//ID is a string of variable length.
	private byte reson;//reson Value(1byte). 
	
	private byte status;
	private String statusItemN;
	
	private String text;
	private byte level;
	
	/**
	 * @return the protocolNumber
	 */
	public byte getProtocolNumber() {
		return protocolNumber;
	}
	
	/**
	 * @param protocolNumber the protocolNumber to set
	 */
	public void setProtocolNumber(byte protocolNumber) {
		this.protocolNumber = protocolNumber;
	}
	
	/**
	 * @return the option
	 */
	public byte getOption() {
		return option;
	}
	
	/**
	 * @param option the option to set
	 */
	public void setOption(byte option) {
		this.option = option;
	}
	
	/**
	 * @return the messageContent
	 */
	public byte[] getMessageContent() {
		return messageContent;
	}
	
	/**
	 * @param messageContent the messageContent to set
	 */
	public void setMessageContent(byte[] messageContent) {
		this.messageContent = messageContent;
	}
	
	/**
	 * @return the addr
	 */
	public int getAddr() {
		return addr;
	}
	
	/**
	 * @param addr the addr to set
	 */
	public void setAddr(int addr) {
		this.addr = addr;
	}
	
	/**
	 * @return the method
	 */
	public byte getMethod() {
		return method;
	}
	
	/**
	 * @param method the method to set
	 */
	public void setMethod(byte method) {
		this.method = method;
	}
	
	/**
	 * @return the type
	 */
	public byte getType() {
		return type;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(byte type) {
		this.type = type;
	}
	
	/**
	 * @return the iD
	 */
	public String getID() {
		return ID;
	}
	
	/**
	 * @param iD the iD to set
	 */
	public void setID(String iD) {
		ID = iD;
	}
	
	/**
	 * @return the reson
	 */
	public byte getReson() {
		return reson;
	}
	
	/**
	 * @param reson the reson to set
	 */
	public void setReson(byte reson) {
		this.reson = reson;
	}

	/**
	 * @return the status
	 */
	public byte getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(byte status) {
		this.status = status;
	}

	/**
	 * @return the statusItemN
	 */
	public String getStatusItemN() {
		return statusItemN;
	}

	/**
	 * @param statusItemN the statusItemN to set
	 */
	public void setStatusItemN(String statusItemN) {
		this.statusItemN = statusItemN;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the level
	 */
	public byte getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(byte level) {
		this.level = level;
	}
	
}
