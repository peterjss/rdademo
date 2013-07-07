/*
 * @(#)GPSProtocol.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.protocol;

import java.util.Arrays;
import java.util.List;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-6
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class GPSRequest 
{
	private byte messageHeader;//Message Header Value(1byte).
	private byte reservedBit;//Message Header Value(1byte).
	private byte deviceType;//Purpose Address Value(1byte). 
	private byte protocolNumber;//Protocol Number Value(1byte). 
	
	private byte option;//Source Address Value(1byte). 
	private short messageLength;//Message Length (2byte). include length of messageContent.
	private byte[] messageContent;//MessageContent Value(0-1500byte). 
	private short checksum;//Checksum Value(2byte). 
	private byte messageTail;//Message Tail Value(1byte). 
	
	private short index;
	private short count;
	private byte[] time;
	
	//gps(57)
	private short gps_index;
	private short gps_deviceStatus1;
	private short gps_deviceStatus2;
	private byte gps_gpsType;
	private short gps_gpsSampleCycle;
	private byte[] gps_gpsTime;//gps_pgsTime Value(6byte).
	private short gps_totalSecond;
	private short gps_moveTime;
	private short gps_moveDeistance;
	private byte gps_gpsCount;
	private List<GPSData> gpsData;
	
	private short gps_uploadCycle;
	
	//init_report(5)
	private byte init_simCount;
	private short init_softwareVersion;
	private byte[] init_diviceTime;//init_diviceTime Value(6byte).
	private int init_iPAddr;
	private byte init_powerOn;
	private short init_mCUResetCnt;
	private short init_gPRSResetCnt1;
	private short init_gPRSResetCnt2;
	private String init_sim1ID;
	private String init_sim2ID;
	
	
	private String file_fileName;
	private int file_numOfBytes;
	private short file_packageLen;
	private int file_packageIndex;
	private byte[] file_fileData;
	private short file_checksum;
	private int file_packageIndexLost;
	
	private String versionString;
	private byte valid1;
	private String apn1;
	private String userName1;
	private String password1;
	private String dialnum1;
	private String dNSIP1;
	private String domainName1;
	private String serverIP1;
	private String serverPort1;
	private String deviceNO1;
	private byte valid2;
	private String userName2;
	private String password2;
	private String dialnum2;
	private String dNSIP2;
	private String domainName2;
	private String serverIP2;
	private String serverPort2;
	private String deviceNO2;
	
	private int com1_baudrate;
	private byte com1_use;
	private byte taskButton;
	private byte barcodeDataType;
	
	
	private short status;
	private String deviceNo;
	private byte netState;
	private byte gpsState;
	
	private byte action;
	private int acitonOpt1;
	private int actionOpt2;
	private byte type;
	
	private int opt1;
	private int opt2;
	private int opt3;
	private int opt4;
	
	private byte logCode;
	private String driverBarcodeNo;
	private String barcodeNo;
	
	/**
	 * @return the messageHeader
	 */
	public byte getMessageHeader() {
		return messageHeader;
	}
	
	/**
	 * @param messageHeader the messageHeader to set
	 */
	public void setMessageHeader(byte messageHeader) {
		this.messageHeader = messageHeader;
	}
	
	/**
	 * @return the reservedBit
	 */
	public byte getReservedBit() {
		return reservedBit;
	}
	
	/**
	 * @param reservedBit the reservedBit to set
	 */
	public void setReservedBit(byte reservedBit) {
		this.reservedBit = reservedBit;
	}
	
	/**
	 * @return the deviceType
	 */
	public byte getDeviceType() {
		return deviceType;
	}
	
	/**
	 * @param deviceType the deviceType to set
	 */
	public void setDeviceType(byte deviceType) {
		this.deviceType = deviceType;
	}
	
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
	 * @return the messageLength
	 */
	public short getMessageLength() {
		return messageLength;
	}
	
	/**
	 * @param messageLength the messageLength to set
	 */
	public void setMessageLength(short messageLength) {
		this.messageLength = messageLength;
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
	 * @return the checksum
	 */
	public short getChecksum() {
		return checksum;
	}
	
	/**
	 * @param checksum the checksum to set
	 */
	public void setChecksum(short checksum) {
		this.checksum = checksum;
	}
	
	/**
	 * @return the messageTail
	 */
	public byte getMessageTail() {
		return messageTail;
	}
	
	/**
	 * @param messageTail the messageTail to set
	 */
	public void setMessageTail(byte messageTail) {
		this.messageTail = messageTail;
	}
	

	/**
	 * @return the gps_index
	 */
	public short getGps_index() {
		return gps_index;
	}

	/**
	 * @param gps_index the gps_index to set
	 */
	public void setGps_index(short gps_index) {
		this.gps_index = gps_index;
	}

	/**
	 * @return the gps_deviceStatus1
	 */
	public short getGps_deviceStatus1() {
		return gps_deviceStatus1;
	}

	/**
	 * @param gps_deviceStatus1 the gps_deviceStatus1 to set
	 */
	public void setGps_deviceStatus1(short gps_deviceStatus1) {
		this.gps_deviceStatus1 = gps_deviceStatus1;
	}

	/**
	 * @return the gps_deviceStatus2
	 */
	public short getGps_deviceStatus2() {
		return gps_deviceStatus2;
	}

	/**
	 * @param gps_deviceStatus2 the gps_deviceStatus2 to set
	 */
	public void setGps_deviceStatus2(short gps_deviceStatus2) {
		this.gps_deviceStatus2 = gps_deviceStatus2;
	}

	/**
	 * @return the gps_gpsType
	 */
	public byte getGps_gpsType() {
		return gps_gpsType;
	}

	/**
	 * @param gps_gpsType the gps_gpsType to set
	 */
	public void setGps_gpsType(byte gps_gpsType) {
		this.gps_gpsType = gps_gpsType;
	}

	/**
	 * @return the gps_gpsSampleCycle
	 */
	public short getGps_gpsSampleCycle() {
		return gps_gpsSampleCycle;
	}

	/**
	 * @param gps_gpsSampleCycle the gps_gpsSampleCycle to set
	 */
	public void setGps_gpsSampleCycle(short gps_gpsSampleCycle) {
		this.gps_gpsSampleCycle = gps_gpsSampleCycle;
	}

	/**
	 * @return the gps_pgsTime
	 */
	public byte[] getGps_gpsTime() {
		return gps_gpsTime;
	}

	/**
	 * @param gps_pgsTime the gps_pgsTime to set
	 */
	public void setGps_gpsTime(byte[] gps_gpsTime) {
		this.gps_gpsTime = gps_gpsTime;
	}

	/**
	 * @return the gps_totalSecond
	 */
	public short getGps_totalSecond() {
		return gps_totalSecond;
	}

	/**
	 * @param gps_totalSecond the gps_totalSecond to set
	 */
	public void setGps_totalSecond(short gps_totalSecond) {
		this.gps_totalSecond = gps_totalSecond;
	}

	/**
	 * @return the gps_moveTime
	 */
	public short getGps_moveTime() {
		return gps_moveTime;
	}

	/**
	 * @param gps_moveTime the gps_moveTime to set
	 */
	public void setGps_moveTime(short gps_moveTime) {
		this.gps_moveTime = gps_moveTime;
	}

	/**
	 * @return the gps_moveDeistance
	 */
	public short getGps_moveDeistance() {
		return gps_moveDeistance;
	}

	/**
	 * @param gps_moveDeistance the gps_moveDeistance to set
	 */
	public void setGps_moveDeistance(short gps_moveDeistance) {
		this.gps_moveDeistance = gps_moveDeistance;
	}

	/**
	 * @return the gps_gpsCount
	 */
	public byte getGps_gpsCount() {
		return gps_gpsCount;
	}

	/**
	 * @param gps_gpsCount the gps_gpsCount to set
	 */
	public void setGps_gpsCount(byte gps_gpsCount) {
		this.gps_gpsCount = gps_gpsCount;
	}


	/**
	 * @return the gpsData
	 */
	public List<GPSData> getGpsData() {
		return gpsData;
	}

	/**
	 * @param gpsData the gpsData to set
	 */
	public void setGpsData(List<GPSData> gpsData) {
		this.gpsData = gpsData;
	}

	/**
	 * @return the init_simCount
	 */
	public byte getInit_simCount() {
		return init_simCount;
	}

	/**
	 * @param init_simCount the init_simCount to set
	 */
	public void setInit_simCount(byte init_simCount) {
		this.init_simCount = init_simCount;
	}

	/**
	 * @return the init_softwareVersion
	 */
	public short getInit_softwareVersion() {
		return init_softwareVersion;
	}

	/**
	 * @param init_softwareVersion the init_softwareVersion to set
	 */
	public void setInit_softwareVersion(short init_softwareVersion) {
		this.init_softwareVersion = init_softwareVersion;
	}

	/**
	 * @return the init_diviceTime
	 */
	public byte[] getInit_diviceTime() {
		return init_diviceTime;
	}

	/**
	 * @param init_diviceTime the init_diviceTime to set
	 */
	public void setInit_diviceTime(byte[] init_diviceTime) {
		this.init_diviceTime = init_diviceTime;
	}

	/**
	 * @return the init_iPAddr
	 */
	public int getInit_iPAddr() {
		return init_iPAddr;
	}

	/**
	 * @param init_iPAddr the init_iPAddr to set
	 */
	public void setInit_iPAddr(int init_iPAddr) {
		this.init_iPAddr = init_iPAddr;
	}

	/**
	 * @return the init_powerOn
	 */
	public byte getInit_powerOn() {
		return init_powerOn;
	}

	/**
	 * @param init_powerOn the init_powerOn to set
	 */
	public void setInit_powerOn(byte init_powerOn) {
		this.init_powerOn = init_powerOn;
	}

	/**
	 * @return the init_mCUResetCnt
	 */
	public short getInit_mCUResetCnt() {
		return init_mCUResetCnt;
	}

	/**
	 * @param init_mCUResetCnt the init_mCUResetCnt to set
	 */
	public void setInit_mCUResetCnt(short init_mCUResetCnt) {
		this.init_mCUResetCnt = init_mCUResetCnt;
	}

	/**
	 * @return the init_gPRSResetCnt1
	 */
	public short getInit_gPRSResetCnt1() {
		return init_gPRSResetCnt1;
	}

	/**
	 * @param init_gPRSResetCnt1 the init_gPRSResetCnt1 to set
	 */
	public void setInit_gPRSResetCnt1(short init_gPRSResetCnt1) {
		this.init_gPRSResetCnt1 = init_gPRSResetCnt1;
	}

	/**
	 * @return the init_gPRSResetCnt2
	 */
	public short getInit_gPRSResetCnt2() {
		return init_gPRSResetCnt2;
	}

	/**
	 * @param init_gPRSResetCnt2 the init_gPRSResetCnt2 to set
	 */
	public void setInit_gPRSResetCnt2(short init_gPRSResetCnt2) {
		this.init_gPRSResetCnt2 = init_gPRSResetCnt2;
	}

	/**
	 * @return the init_sim1ID
	 */
	public String getInit_sim1ID() {
		return init_sim1ID;
	}

	/**
	 * @param init_sim1ID the init_sim1ID to set
	 */
	public void setInit_sim1ID(String init_sim1ID) {
		this.init_sim1ID = init_sim1ID;
	}

	/**
	 * @return the init_sim2ID
	 */
	public String getInit_sim2ID() {
		return init_sim2ID;
	}

	/**
	 * @param init_sim2ID the init_sim2ID to set
	 */
	public void setInit_sim2ID(String init_sim2ID) {
		this.init_sim2ID = init_sim2ID;
	}

	/**
	 * @return the file_fileName
	 */
	public String getFile_fileName() {
		return file_fileName;
	}

	/**
	 * @param file_fileName the file_fileName to set
	 */
	public void setFile_fileName(String file_fileName) {
		this.file_fileName = file_fileName;
	}

	/**
	 * @return the file_numOfBytes
	 */
	public int getFile_numOfBytes() {
		return file_numOfBytes;
	}

	/**
	 * @param file_numOfBytes the file_numOfBytes to set
	 */
	public void setFile_numOfBytes(int file_numOfBytes) {
		this.file_numOfBytes = file_numOfBytes;
	}

	/**
	 * @return the file_packageLen
	 */
	public short getFile_packageLen() {
		return file_packageLen;
	}

	/**
	 * @param file_packageLen the file_packageLen to set
	 */
	public void setFile_packageLen(short file_packageLen) {
		this.file_packageLen = file_packageLen;
	}

	/**
	 * @return the file_packageIndex
	 */
	public int getFile_packageIndex() {
		return file_packageIndex;
	}

	/**
	 * @param file_packageIndex the file_packageIndex to set
	 */
	public void setFile_packageIndex(int file_packageIndex) {
		this.file_packageIndex = file_packageIndex;
	}

	/**
	 * @return the file_fileData
	 */
	public byte[] getFile_fileData() {
		return file_fileData;
	}

	/**
	 * @param file_fileData the file_fileData to set
	 */
	public void setFile_fileData(byte[] file_fileData) {
		this.file_fileData = file_fileData;
	}

	/**
	 * @return the file_checksum
	 */
	public short getFile_checksum() {
		return file_checksum;
	}

	/**
	 * @param file_checksum the file_checksum to set
	 */
	public void setFile_checksum(short file_checksum) {
		this.file_checksum = file_checksum;
	}

	/**
	 * @return the file_packageIndexLost
	 */
	public int getFile_packageIndexLost() {
		return file_packageIndexLost;
	}

	/**
	 * @param file_packageIndexLost the file_packageIndexLost to set
	 */
	public void setFile_packageIndexLost(int file_packageIndexLost) {
		this.file_packageIndexLost = file_packageIndexLost;
	}

	/**
	 * @return the index
	 */
	public short getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(short index) {
		this.index = index;
	}

	/**
	 * @return the count
	 */
	public short getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(short count) {
		this.count = count;
	}

	/**
	 * @return the time
	 */
	public byte[] getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(byte[] time) {
		this.time = time;
	}

	/**
	 * @return the gps_uploadCycle
	 */
	public short getGps_uploadCycle() {
		return gps_uploadCycle;
	}

	/**
	 * @param gps_uploadCycle the gps_uploadCycle to set
	 */
	public void setGps_uploadCycle(short gps_uploadCycle) {
		this.gps_uploadCycle = gps_uploadCycle;
	}

	/**
	 * @return the versionString
	 */
	public String getVersionString() {
		return versionString;
	}

	/**
	 * @param versionString the versionString to set
	 */
	public void setVersionString(String versionString) {
		this.versionString = versionString;
	}

	/**
	 * @return the valid1
	 */
	public byte getValid1() {
		return valid1;
	}

	/**
	 * @param valid1 the valid1 to set
	 */
	public void setValid1(byte valid1) {
		this.valid1 = valid1;
	}

	/**
	 * @return the apn1
	 */
	public String getApn1() {
		return apn1;
	}

	/**
	 * @param apn1 the apn1 to set
	 */
	public void setApn1(String apn1) {
		this.apn1 = apn1;
	}

	/**
	 * @return the userName1
	 */
	public String getUserName1() {
		return userName1;
	}

	/**
	 * @param userName1 the userName1 to set
	 */
	public void setUserName1(String userName1) {
		this.userName1 = userName1;
	}

	/**
	 * @return the password1
	 */
	public String getPassword1() {
		return password1;
	}

	/**
	 * @param password1 the password1 to set
	 */
	public void setPassword1(String password1) {
		this.password1 = password1;
	}

	/**
	 * @return the dialnum1
	 */
	public String getDialnum1() {
		return dialnum1;
	}

	/**
	 * @param dialnum1 the dialnum1 to set
	 */
	public void setDialnum1(String dialnum1) {
		this.dialnum1 = dialnum1;
	}

	/**
	 * @return the dNSIP1
	 */
	public String getdNSIP1() {
		return dNSIP1;
	}

	/**
	 * @param dNSIP1 the dNSIP1 to set
	 */
	public void setdNSIP1(String dNSIP1) {
		this.dNSIP1 = dNSIP1;
	}

	/**
	 * @return the domainName1
	 */
	public String getDomainName1() {
		return domainName1;
	}

	/**
	 * @param domainName1 the domainName1 to set
	 */
	public void setDomainName1(String domainName1) {
		this.domainName1 = domainName1;
	}

	/**
	 * @return the serverIP1
	 */
	public String getServerIP1() {
		return serverIP1;
	}

	/**
	 * @param serverIP1 the serverIP1 to set
	 */
	public void setServerIP1(String serverIP1) {
		this.serverIP1 = serverIP1;
	}

	/**
	 * @return the serverPort1
	 */
	public String getServerPort1() {
		return serverPort1;
	}

	/**
	 * @param serverPort1 the serverPort1 to set
	 */
	public void setServerPort1(String serverPort1) {
		this.serverPort1 = serverPort1;
	}

	/**
	 * @return the deviceNO1
	 */
	public String getDeviceNO1() {
		return deviceNO1;
	}

	/**
	 * @param deviceNO1 the deviceNO1 to set
	 */
	public void setDeviceNO1(String deviceNO1) {
		this.deviceNO1 = deviceNO1;
	}

	/**
	 * @return the valid2
	 */
	public byte getValid2() {
		return valid2;
	}

	/**
	 * @param valid2 the valid2 to set
	 */
	public void setValid2(byte valid2) {
		this.valid2 = valid2;
	}

	/**
	 * @return the userName2
	 */
	public String getUserName2() {
		return userName2;
	}

	/**
	 * @param userName2 the userName2 to set
	 */
	public void setUserName2(String userName2) {
		this.userName2 = userName2;
	}

	/**
	 * @return the password2
	 */
	public String getPassword2() {
		return password2;
	}

	/**
	 * @param password2 the password2 to set
	 */
	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	/**
	 * @return the dialnum2
	 */
	public String getDialnum2() {
		return dialnum2;
	}

	/**
	 * @param dialnum2 the dialnum2 to set
	 */
	public void setDialnum2(String dialnum2) {
		this.dialnum2 = dialnum2;
	}

	/**
	 * @return the dNSIP2
	 */
	public String getdNSIP2() {
		return dNSIP2;
	}

	/**
	 * @param dNSIP2 the dNSIP2 to set
	 */
	public void setdNSIP2(String dNSIP2) {
		this.dNSIP2 = dNSIP2;
	}

	/**
	 * @return the domainName2
	 */
	public String getDomainName2() {
		return domainName2;
	}

	/**
	 * @param domainName2 the domainName2 to set
	 */
	public void setDomainName2(String domainName2) {
		this.domainName2 = domainName2;
	}

	/**
	 * @return the serverIP2
	 */
	public String getServerIP2() {
		return serverIP2;
	}

	/**
	 * @param serverIP2 the serverIP2 to set
	 */
	public void setServerIP2(String serverIP2) {
		this.serverIP2 = serverIP2;
	}

	/**
	 * @return the serverPort2
	 */
	public String getServerPort2() {
		return serverPort2;
	}

	/**
	 * @param serverPort2 the serverPort2 to set
	 */
	public void setServerPort2(String serverPort2) {
		this.serverPort2 = serverPort2;
	}

	/**
	 * @return the deviceNO2
	 */
	public String getDeviceNO2() {
		return deviceNO2;
	}

	/**
	 * @param deviceNO2 the deviceNO2 to set
	 */
	public void setDeviceNO2(String deviceNO2) {
		this.deviceNO2 = deviceNO2;
	}

	/**
	 * @return the com1_baudrate
	 */
	public int getCom1_baudrate() {
		return com1_baudrate;
	}

	/**
	 * @param com1_baudrate the com1_baudrate to set
	 */
	public void setCom1_baudrate(int com1_baudrate) {
		this.com1_baudrate = com1_baudrate;
	}

	/**
	 * @return the com1_use
	 */
	public byte getCom1_use() {
		return com1_use;
	}

	/**
	 * @param com1_use the com1_use to set
	 */
	public void setCom1_use(byte com1_use) {
		this.com1_use = com1_use;
	}

	/**
	 * @return the taskButton
	 */
	public byte getTaskButton() {
		return taskButton;
	}

	/**
	 * @param taskButton the taskButton to set
	 */
	public void setTaskButton(byte taskButton) {
		this.taskButton = taskButton;
	}

	/**
	 * @return the barcodeDataType
	 */
	public byte getBarcodeDataType() {
		return barcodeDataType;
	}

	/**
	 * @param barcodeDataType the barcodeDataType to set
	 */
	public void setBarcodeDataType(byte barcodeDataType) {
		this.barcodeDataType = barcodeDataType;
	}

	/**
	 * @return the status
	 */
	public short getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(short status) {
		this.status = status;
	}

	/**
	 * @return the deviceNo
	 */
	public String getDeviceNo() {
		return deviceNo;
	}

	/**
	 * @param deviceNo the deviceNo to set
	 */
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	/**
	 * @return the netState
	 */
	public byte getNetState() {
		return netState;
	}

	/**
	 * @param netState the netState to set
	 */
	public void setNetState(byte netState) {
		this.netState = netState;
	}

	/**
	 * @return the gpsState
	 */
	public byte getGpsState() {
		return gpsState;
	}

	/**
	 * @param gpsState the gpsState to set
	 */
	public void setGpsState(byte gpsState) {
		this.gpsState = gpsState;
	}

	/**
	 * @return the action
	 */
	public byte getAction() {
		return action;
	}

	/**
	 * @param action the action to set
	 */
	public void setAction(byte action) {
		this.action = action;
	}

	/**
	 * @return the acitonOpt1
	 */
	public int getAcitonOpt1() {
		return acitonOpt1;
	}

	/**
	 * @param acitonOpt1 the acitonOpt1 to set
	 */
	public void setAcitonOpt1(int acitonOpt1) {
		this.acitonOpt1 = acitonOpt1;
	}

	/**
	 * @return the actionOpt2
	 */
	public int getActionOpt2() {
		return actionOpt2;
	}

	/**
	 * @param actionOpt2 the actionOpt2 to set
	 */
	public void setActionOpt2(int actionOpt2) {
		this.actionOpt2 = actionOpt2;
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
	 * @return the opt1
	 */
	public int getOpt1() {
		return opt1;
	}

	/**
	 * @param opt1 the opt1 to set
	 */
	public void setOpt1(int opt1) {
		this.opt1 = opt1;
	}

	/**
	 * @return the opt2
	 */
	public int getOpt2() {
		return opt2;
	}

	/**
	 * @param opt2 the opt2 to set
	 */
	public void setOpt2(int opt2) {
		this.opt2 = opt2;
	}

	/**
	 * @return the opt3
	 */
	public int getOpt3() {
		return opt3;
	}

	/**
	 * @param opt3 the opt3 to set
	 */
	public void setOpt3(int opt3) {
		this.opt3 = opt3;
	}

	/**
	 * @return the opt4
	 */
	public int getOpt4() {
		return opt4;
	}

	/**
	 * @param opt4 the opt4 to set
	 */
	public void setOpt4(int opt4) {
		this.opt4 = opt4;
	}

	/**
	 * @return the logCode
	 */
	public byte getLogCode() {
		return logCode;
	}

	/**
	 * @param logCode the logCode to set
	 */
	public void setLogCode(byte logCode) {
		this.logCode = logCode;
	}

	/**
	 * @return the driverBarcodeNo
	 */
	public String getDriverBarcodeNo() {
		return driverBarcodeNo;
	}

	/**
	 * @param driverBarcodeNo the driverBarcodeNo to set
	 */
	public void setDriverBarcodeNo(String driverBarcodeNo) {
		this.driverBarcodeNo = driverBarcodeNo;
	}

	/**
	 * @return the barcodeNo
	 */
	public String getBarcodeNo() {
		return barcodeNo;
	}

	/**
	 * @param barcodeNo the barcodeNo to set
	 */
	public void setBarcodeNo(String barcodeNo) {
		this.barcodeNo = barcodeNo;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GPSRequest [messageHeader=" + messageHeader + ", reservedBit="
				+ reservedBit + ", deviceType=" + deviceType
				+ ", protocolNumber=" + protocolNumber + ", option=" + option
				+ ", messageLength=" + messageLength + ", messageContent="
				+ Arrays.toString(messageContent) + ", checksum=" + checksum
				+ ", messageTail=" + messageTail + ", index=" + index
				+ ", count=" + count + ", time=" + Arrays.toString(time)
				+ ", gps_index=" + gps_index + ", gps_deviceStatus1="
				+ gps_deviceStatus1 + ", gps_deviceStatus2="
				+ gps_deviceStatus2 + ", gps_gpsType=" + gps_gpsType
				+ ", gps_gpsSampleCycle=" + gps_gpsSampleCycle
				+ ", gps_gpsTime=" + Arrays.toString(gps_gpsTime)
				+ ", gps_totalSecond=" + gps_totalSecond + ", gps_moveTime="
				+ gps_moveTime + ", gps_moveDeistance=" + gps_moveDeistance
				+ ", gps_gpsCount=" + gps_gpsCount + ", gpsData=" + gpsData
				+ ", gps_uploadCycle=" + gps_uploadCycle + ", init_simCount="
				+ init_simCount + ", init_softwareVersion="
				+ init_softwareVersion + ", init_diviceTime="
				+ Arrays.toString(init_diviceTime) + ", init_iPAddr="
				+ init_iPAddr + ", init_powerOn=" + init_powerOn
				+ ", init_mCUResetCnt=" + init_mCUResetCnt
				+ ", init_gPRSResetCnt1=" + init_gPRSResetCnt1
				+ ", init_gPRSResetCnt2=" + init_gPRSResetCnt2
				+ ", init_sim1ID=" + init_sim1ID + ", init_sim2ID="
				+ init_sim2ID + ", file_fileName=" + file_fileName
				+ ", file_numOfBytes=" + file_numOfBytes + ", file_packageLen="
				+ file_packageLen + ", file_packageIndex=" + file_packageIndex
				+ ", file_fileData=" + Arrays.toString(file_fileData)
				+ ", file_checksum=" + file_checksum
				+ ", file_packageIndexLost=" + file_packageIndexLost
				+ ", versionString=" + versionString + ", valid1=" + valid1
				+ ", apn1=" + apn1 + ", userName1=" + userName1
				+ ", password1=" + password1 + ", dialnum1=" + dialnum1
				+ ", dNSIP1=" + dNSIP1 + ", domainName1=" + domainName1
				+ ", serverIP1=" + serverIP1 + ", serverPort1=" + serverPort1
				+ ", deviceNO1=" + deviceNO1 + ", valid2=" + valid2
				+ ", userName2=" + userName2 + ", password2=" + password2
				+ ", dialnum2=" + dialnum2 + ", dNSIP2=" + dNSIP2
				+ ", domainName2=" + domainName2 + ", serverIP2=" + serverIP2
				+ ", serverPort2=" + serverPort2 + ", deviceNO2=" + deviceNO2
				+ ", com1_baudrate=" + com1_baudrate + ", com1_use=" + com1_use
				+ ", taskButton=" + taskButton + ", barcodeDataType="
				+ barcodeDataType + ", status=" + status + ", deviceNo="
				+ deviceNo + ", netState=" + netState + ", gpsState="
				+ gpsState + ", action=" + action + ", acitonOpt1="
				+ acitonOpt1 + ", actionOpt2=" + actionOpt2 + ", type=" + type
				+ ", opt1=" + opt1 + ", opt2=" + opt2 + ", opt3=" + opt3
				+ ", opt4=" + opt4 + ", logCode=" + logCode
				+ ", driverBarcodeNo=" + driverBarcodeNo + ", barcodeNo="
				+ barcodeNo + "]";
	}
	
}
