/*
 * @(#)McardIchManager.java
 *
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard;


import com.iaspec.rda.foundation.common.server.AbstractIchManager;



/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardIchManager extends AbstractIchManager
{
	private final String name = MycardIchManager.class.getSimpleName();
	
    @Override
    public String getName()
    {
        return name;
    }

}
