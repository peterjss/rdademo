package com.iaspec.rda.foundation.common.server;


/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public class IchServerConfig
{
    private int port;
    private String serverName;
    private String serverType;
    

    /**
	 * @return the serverType
	 */
	public String getServerType() {
		return serverType;
	}

	/**
	 * @param serverType the serverType to set
	 */
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}

	public int getPort()
    {
        return port;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public String getServerName()
    {
        return serverName;
    }

    public void setServerName(String serverName)
    {
        this.serverName = serverName;
    }
}
