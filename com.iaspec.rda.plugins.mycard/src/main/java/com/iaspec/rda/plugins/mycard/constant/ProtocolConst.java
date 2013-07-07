/*
 * @(#)ProtocolConst.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.constant;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-7
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class ProtocolConst 
{
	//**************************Platform Protocol******************************///
	public static final int DATA_TRANSMISSION =1;
	public static final int LINK_CONTROL =2;
	public static final int SYSTEM_CONTROL =3;
	public static final int COMMON_CONTROL =4;
	public static final int BROADCAST_DATA_TRANSMISSION =5;
	public static final int DATA_TRANSMISSION_AND_LINK_CONTROL =6;
	
	//DATA_TRANSMISSION
	public static final int DATA_TRA_SYS =1;
	public static final int DATA_TRA_CAR =2;
	public static final int DATA_TRA_CLIENT =3;

	//LINK_CONTROL
	public static final int LINK_REQ =1;
	public static final int LINK_RES_OK =2;
	public static final int LINK_DEST_NOT_REACH =31;
	public static final int LINK_DEST_UNKNOW =32;
	public static final int LINK_DATA_FORMAT_ERROR =41;
	public static final int LINK_CHECKSUM_ERROR =42;
	
    //SYSTEM_CONTROL
	public static final int SYSTEM_DEST_REQ =1;
	public static final int SYSTEM_DEST_RES_OK =20;
	public static final int SYSTEM_DEST_RES_FAIL =21;
	public static final int SYSTEM_ADDR_ASSIGN_REQ =3;
	public static final int SYSTEM_ADDR_ASSIGN_RES_OK =40;
	public static final int SYSTEM_ADDR_ASSIGN_RES_FAIL =41;
	public static final int SYSTEM_LOG_REQ =5;
	public static final int SYSTEM_LOG_RES_OK =60;
	public static final int SYSTEM_LOG_RES_FAIL =61;
	
	//*********************GPS Protocol*********************************//
	//COMMON PROTOCOL
	public static final int INIT_REPORT =5;
	public static final int FILE_TRAN_START =9;
	public static final int FIEL_TRAN =11;
	public static final int FILE_TRAN_END =13;
	public static final int FILE_CHECK_LOST=16;
	
	//CONTROL PROTOCOL
	public static final int READ_VERSION=17;
	public static final int ANWSER_VERSION_SUC=18;
	public static final int ANWSER_NET_PARA=20;//PARA =PARAMETER
	public static final int SET_NET_PARA=21;
	public static final int READ_WORK_PARA=23;
	public static final int ANSWER_WORK_PARA_SUC=24;
	public static final int SET_WORK_PARA =25;
	public static final int SWITCH_DEVICE_STATUS=33;
	public static final int SWITCH_DEVICE_STATUS_RES=34;
	public static final int READ_CLOCK_SUC=44;
	public static final int SET_CLOCK=45;
	public static final int READ_MONIT_SUC=48;
	public static final int SET_MONIT =49;
	
	//WORK DATA TRANSMISSION
	public static final int TRAN_LOG =53;
	public static final int TRAN_GPS_DATA =57;
	
	//BUSINESS DATA TRANSMISSION
	public static final int REQ_START_BUSINESS =150;
	public static final int REQ_END_BUSINESS =152;
	public static final int UPLOAD_BARCODE =182;
	public static final int UPLOAD_BARCODE_RES =183;
}
