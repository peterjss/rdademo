

package com.iaspec.rda.plugins.mycard.instance1;


import java.net.InetSocketAddress;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;

import com.iaspec.rda.foundation.common.server.AbstractIchServer;
import com.iaspec.rda.foundation.common.server.IServerHandler;
import com.iaspec.rda.foundation.common.server.IchServerConfig;
import com.iaspec.rda.plugins.mycard.external.server.IMycardIchServer;
import com.iaspec.rda.plugins.mycard.instance1.util.ServerConfig;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardIchServer extends AbstractIchServer implements IMycardIchServer
{
    private final String serverName = MycardIchServer.class.getSimpleName();

    public MycardIchServer()
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
			config.setServerType(SERVER_TYPE_UDP);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return config;
	}

	@Override
	public IServerHandler createHandler()
	{
		return (IServerHandler) new MycardIchInstanceHandler();
	}
}

