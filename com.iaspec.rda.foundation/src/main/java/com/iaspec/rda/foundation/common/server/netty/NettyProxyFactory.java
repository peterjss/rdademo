

package com.iaspec.rda.foundation.common.server.netty;


import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.ChannelGroupFuture;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.iaspec.rda.foundation.common.server.IBaseFrameworkProxyFactory;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public class NettyProxyFactory implements IBaseFrameworkProxyFactory
{
	private ServerBootstrap bootstrap;
    private ConnectionlessBootstrap udpBootstrap;
	private ChannelHandler channelHandler;

	public static final ChannelGroup channels = new DefaultChannelGroup();

	@Override
	public void startTcpServer(int port) throws Exception
	{
		// Configure the server.
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
		        Executors.newCachedThreadPool()));
		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception
			{
				return Channels.pipeline(channelHandler);
			}
		});

		// Bind and start to accept incoming connections.
		bootstrap.bind(new InetSocketAddress(port));// ServerConfig.getInstance().port()));
	}

	@Override
	public void startUdpServer(int port) throws Exception
	{
		// Configure the server.
		 DatagramChannelFactory channelFactory = new NioDatagramChannelFactory();
         udpBootstrap = new ConnectionlessBootstrap(channelFactory);
		
         // Set up the event pipeline factory.
		udpBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception
			{
				return Channels.pipeline(channelHandler);
			}
		});

		// Bind and start to accept incoming connections.
		udpBootstrap.bind(new InetSocketAddress(port));// ServerConfig.getInstance().port()));
	}
	
	@Override
	public void shutdown()
	{
		ChannelGroupFuture future = channels.disconnect();
		future.awaitUninterruptibly();
		if (bootstrap != null) {
			bootstrap.releaseExternalResources();
		}
	}

	@Override
	public void setChannelHandler(ChannelHandler channelHandler)
	{
		this.channelHandler = channelHandler;
	}

	public void addChannel(Channel e)
	{
		channels.add(e);
	}

}
