/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.instance1;


import com.iaspec.rda.foundation.common.server.AbstractIchServer;
import com.iaspec.rda.foundation.common.server.IServerHandler;
import com.iaspec.rda.foundation.common.server.IchServerConfig;
import com.iaspec.rda.plugins.rfid.external.server.IRFIDIchServer;
import com.iaspec.rda.plugins.rfid.instance1.util.ServerConfig;


/**
 * Serves two protocols (HTTP and Factorial) using only one port, enabling
 * either SSL or GZIP dynamically on demand.
 * <p/>
 * Because SSL and GZIP are enabled on demand, 5 combinations per protocol are
 * possible: none, SSL only, GZIP only, SSL + GZIP, and GZIP + SSL.
 */
public class RFIDIchServer extends AbstractIchServer implements IRFIDIchServer
{
	private final String serverName = RFIDIchServer.class.getSimpleName();

	public RFIDIchServer()
	{
	}

	@Override
	public String getName()
	{
		return serverName;
	}

	@Override
	public IchServerConfig createConfig()
	{
		IchServerConfig config = new IchServerConfig();

		try {
			ServerConfig sc = ServerConfig.getInstance();
			int port = sc.port();
			config.setServerName(sc.server());
			config.setPort(port);
			config.setServerType(SERVER_TYPE_TCP);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return config;
	}

	@Override
	public IServerHandler createHandler()
	{
		return (IServerHandler) new RFIDIchInstanceHandler();
	}
}