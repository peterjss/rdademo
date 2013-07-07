/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.foundation.message;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public class ExecutorConfig
{
	private Integer corePoolSize;
	private Integer maximumPoolSize;
	private Integer keepAliveSecond;

	public Integer getCorePoolSize()
	{
		return corePoolSize;
	}

	public void setCorePoolSize(Integer corePoolSize)
	{
		this.corePoolSize = corePoolSize;
	}

	public Integer getMaximumPoolSize()
	{
		return maximumPoolSize;
	}

	public void setMaximumPoolSize(Integer maximumPoolSize)
	{
		this.maximumPoolSize = maximumPoolSize;
	}

	public Integer getKeepAliveSecond()
	{
		return keepAliveSecond;
	}

	public void setKeepAliveSecond(Integer keepAliveSecond)
	{
		this.keepAliveSecond = keepAliveSecond;
	}
}
