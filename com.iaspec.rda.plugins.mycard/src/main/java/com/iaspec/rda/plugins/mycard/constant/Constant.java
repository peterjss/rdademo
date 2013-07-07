/*
 * @(#)Constant.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.constant;



/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 * @version 1.00 13-3-7
 */

public class Constant 
{
	//**************************Platform Protocol******************************///
	public static final String DATA_TRANSMISSION ="DATA_TRANSMISSION";
	public static final String LINK_CONTROL ="LINK_CONTROL";
	public static final String SYSTEM_CONTROL ="SYSTEM_CONTROL";
	public static final String COMMON_CONTROL ="COMMON_CONTROL";
	public static final String BROADCAST_DATA_TRANSMISSION ="BROADCAST_DATA_TRANSMISSION";
	public static final String DATA_TRANSMISSION_AND_LINK_CONTROL ="DATA_TRANSMISSION_AND_LINK_CONTROL";
	
	//DATA_TRANSMISSION
	public static final String DATA_TRA_SYS ="DATA_TRA_SYS";
	public static final String DATA_TRA_CAR ="DATA_TRA_CAR";
	public static final String DATA_TRA_CLIENT ="DATA_TRA_CLIENT";

	//LINK_CONTROL
	public static final String LINK_REQ ="LINK_REQ";
	public static final String LINK_RES_OK ="LINK_RES_OK";
	public static final String LINK_DEST_NOT_REACH ="LINK_DEST_NOT_REACH";
	public static final String LINK_DEST_UNKNOW ="LINK_DEST_UNKNOW";
	public static final String LINK_DATA_FORMAT_ERROR ="LINK_DATA_FORMAT_ERROR";
	public static final String LINK_CHECKSUM_ERROR ="LINK_CHECKSUM_ERROR";
	
    //SYSTEM_CONTROL
	public static final String SYSTEM_DEST_REQ ="SYSTEM_DEST_REQ";
	public static final String SYSTEM_DEST_RES_OK ="SYSTEM_DEST_RES_OK";
	public static final String SYSTEM_DEST_RES_FAIL ="SYSTEM_DEST_RES_FAIL";
	public static final String SYSTEM_ADDR_ASSIGN_REQ ="SYSTEM_ADDR_ASSIGN_REQ";
	public static final String SYSTEM_ADDR_ASSIGN_RES_OK ="SYSTEM_ADDR_ASSIGN_RES_OK";
	public static final String SYSTEM_ADDR_ASSIGN_RES_FAIL ="SYSTEM_ADDR_ASSIGN_RES_FAIL";
	public static final String SYSTEM_LOG_REQ ="SYSTEM_LOG_REQ";
	public static final String SYSTEM_LOG_RES_OK ="SYSTEM_LOG_RES_OK";
	public static final String SYSTEM_LOG_RES_FAIL ="SYSTEM_LOG_RES_FAIL";
	
	//*********************GPS Protocol*********************************//
	//COMMON PROTOCOL
	public static final String INIT_REPORT ="INIT_REPORT";
	public static final String FILE_TRAN_START ="FILE_TRAN_START";
	public static final String FIEL_TRAN ="FIEL_TRAN";
	public static final String FILE_TRAN_END ="FILE_TRAN_END";
	public static final String FILE_CHECK_LOST="FILE_CHECK_LOST";
	
	//CONTROL PROTOCOL
	public static final String READ_VERSION="READ_VERSION";
	public static final String ANWSER_VERSION_SUC="ANWSER_VERSION_SUC";
	public static final String ANWSER_NET_PARA="ANWSER_NET_PARA";
	public static final String SET_NET_PARA="SET_NET_PARA";
	public static final String READ_WORK_PARA="READ_WORK_PARA";//PARA =PARAMETER
	public static final String ANSWER_WORK_PARA_SUC="ANSWER_WORK_PARA_SUC";//PARA =PARAMETER
	public static final String SET_WORK_PARA ="SET_WORK_PARA";
	public static final String SWITCH_DEVICE_STATUS="SWITCH_DEVICE_STATUS";
	public static final String SWITCH_DEVICE_STATUS_RES="SWITCH_DEVICE_STATUS_RES";
	public static final String READ_CLOCK_SUC="READ_CLOCK_SUC";
	public static final String SET_CLOCK="SET_CLOCK";
	public static final String READ_MONIT_SUC="READ_MONIT_SUC";
	public static final String SET_MONIT ="SET_MONIT";
	
	//WORK DATA TRANSMISSION
	public static final String TRAN_LOG ="TRAN_LOG";
	public static final String TRAN_GPS_DATA ="TRAN_GPS_DATA";
	
	//BUSINESS DATA TRANSMISSION
	public static final String REQ_START_BUSINESS ="REQ_START_BUSINESS";
	public static final String REQ_END_BUSINESS ="REQ_END_BUSINESS";
	public static final String UPLOAD_BARCODE ="UPLOAD_BARCODE";
	public static final String UPLOAD_BARCODE_RES ="UPLOAD_BARCODE_RES";
	
	
}
