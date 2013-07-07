/*
 * @(#)GPSData.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.protocol;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-3-13
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class GPSData 
{
	private int gps_longitude;
	private int gps_latitude;
	private byte gps_speed;
	private byte gps_derection;
	
	
	/**
	 * @return the gps_longitude
	 */
	public int getGps_longitude() {
		return gps_longitude;
	}
	
	/**
	 * @param gps_longitude the gps_longitude to set
	 */
	public void setGps_longitude(int gps_longitude) {
		this.gps_longitude = gps_longitude;
	}
	
	/**
	 * @return the gps_latitude
	 */
	public int getGps_latitude() {
		return gps_latitude;
	}
	
	/**
	 * @param gps_latitude the gps_latitude to set
	 */
	public void setGps_latitude(int gps_latitude) {
		this.gps_latitude = gps_latitude;
	}
	
	/**
	 * @return the gps_speed
	 */
	public byte getGps_speed() {
		return gps_speed;
	}
	
	/**
	 * @param gps_speed the gps_speed to set
	 */
	public void setGps_speed(byte gps_speed) {
		this.gps_speed = gps_speed;
	}
	
	/**
	 * @return the gps_derection
	 */
	public byte getGps_derection() {
		return gps_derection;
	}
	
	/**
	 * @param gps_derection the gps_derection to set
	 */
	public void setGps_derection(byte gps_derection) {
		this.gps_derection = gps_derection;
	}
	
}
