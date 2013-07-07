

package com.iaspec.rda.foundation.common.server;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class description goes here. User: Peter
 * 
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public abstract class AbstractIchManager implements IIchManager
{
	private static final Logger logger = LoggerFactory.getLogger(AbstractIchManager.class);

	private Map<String, IIchServer> ichMap = new ConcurrentHashMap<String, IIchServer>();

	public void addIchServer(IIchServer server) throws Exception
	{
		// logger.info("Starting the plugin server...");
		if (!ichMap.containsKey(server.getName())) {
			ichMap.put(server.getName(), server);
			// logger.info("Started.");
			server.run();
		}
	}

	public void removeIchServer(IIchServer server) throws Exception
	{
		if (ichMap.containsKey(server.getName())) {
			server.shutdown();
			ichMap.remove(server.getName());
		}
	}

	public void showServices()
	{
		logger.info("Show :" + ichMap.size());
		Iterator<String> keys = ichMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			System.out.println("-" + key);
		}
	}

	public abstract String getName();

	@Override
	public void runService(String name)
	{
		ichMap.containsKey(name);
		IIchServer ichServer = ichMap.get(name);
		ichServer.run();
	}
}
