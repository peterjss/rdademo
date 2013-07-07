/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid;


import com.iaspec.rda.foundation.common.server.AbstractIchManager;


/**
 * @author Peter
 */
public class RFIDIchManager extends AbstractIchManager
{

	private final String name = RFIDIchManager.class.getSimpleName();

	@Override
	public String getName()
	{
		return name;
	}

}
