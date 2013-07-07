

package com.iaspec.rda.plugins.rfid.external.message;


import com.iaspec.rda.message.IMessageRequest;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-28
 */
public class RfidMessageRequest implements IMessageRequest
{

	private String sessionId;
	private String data;

	public RfidMessageRequest(String sessionId)
	{
		this.sessionId = sessionId;
	}

	@Override
	public String getSessionId()
	{
		return sessionId;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}

}
