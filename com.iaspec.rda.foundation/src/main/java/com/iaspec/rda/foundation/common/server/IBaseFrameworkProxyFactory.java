

package com.iaspec.rda.foundation.common.server;


import org.jboss.netty.channel.ChannelHandler;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public interface IBaseFrameworkProxyFactory
{
	public void startTcpServer(int port) throws Exception;
	
	public void startUdpServer(int port) throws Exception;

	public void shutdown();

	public void setChannelHandler(ChannelHandler channelHandler);

}
