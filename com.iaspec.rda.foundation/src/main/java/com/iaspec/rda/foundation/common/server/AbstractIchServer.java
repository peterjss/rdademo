

package com.iaspec.rda.foundation.common.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.foundation.common.server.netty.NettyProxyFactory;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public abstract class AbstractIchServer implements IIchServer
{
	protected static String SERVER_TYPE_TCP = "TCP";
	protected static String SERVER_TYPE_UDP = "UDP";
	
	protected static final Logger logger = LoggerFactory.getLogger(AbstractIchServer.class);
	private IBaseFrameworkProxyFactory nettyProxyFactory = new NettyProxyFactory();

	public AbstractIchServer()
	{
	}

	@Override
	public void run()
	{
		// Configure the server.
		IchServerConfig config = createConfig();
		logger.info("Starting {} Server...", config.getServerName());

		try {
			nettyProxyFactory.setChannelHandler(createHandler());
			
			if(config.getServerType().equals(SERVER_TYPE_TCP))
			{
				nettyProxyFactory.startTcpServer(config.getPort());
				
			}else if(config.getServerType().equals(SERVER_TYPE_UDP))
			{
				nettyProxyFactory.startUdpServer(config.getPort());
			}
			
			logger.info("port : {}", config.getPort());	
			logger.info("Started.");
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	@Override
	public void shutdown()
	{
		nettyProxyFactory.shutdown();
	}

	public abstract IchServerConfig createConfig();

	public abstract IServerHandler createHandler();

	public abstract String getName();

}
