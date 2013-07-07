/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.main;


import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.eclipse.osgi.framework.console.CommandInterpreter;
import org.eclipse.osgi.framework.console.CommandProvider;

import com.iaspec.rda.foundation.common.server.IIchManager;


public class RDAManager implements CommandProvider
{

	// private static final Logger logger =
	// LoggerFactory.getLogger(RDAManager.class);

	private Map<String, IIchManager> ichMap = new ConcurrentHashMap<String, IIchManager>();

	public void addIchManager(IIchManager manager) throws Exception
	{
		// logger.info("Starting the RDA Server......");
		if (!ichMap.containsKey(manager.getName())) {
			// logger.info("Affiliate with the Protocol Instance....");
			ichMap.put(manager.getName(), manager);
			// logger.info("OK");
		}
	}

	public void removeIchManager(IIchManager manager) throws Exception
	{
		if (ichMap.containsKey(manager.getName())) {
			ichMap.remove(manager);
		}
	}

	public void _showIchManagers(CommandInterpreter ci)
	{
		Iterator<String> keys = ichMap.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			ci.println("key**:" + key);
		}
	}

	public void _showIchServices(CommandInterpreter ci)
	{
		String ichName = ci.nextArgument();
		if (ichMap.containsKey(ichName)) {
			IIchManager bs = (IIchManager) ichMap.get(ichName);
			bs.showServices();
		}
		else {
			ci.println(getHelp());
		}
	}

	public void _runIchService(CommandInterpreter ci)
	{
		String ichManagerName = ci.nextArgument();
		String ichServerName = ci.nextArgument();

		if (ichMap.containsKey(ichManagerName)) {
			IIchManager bs = (IIchManager) ichMap.get(ichManagerName);
			bs.runService(ichServerName);
			// bs.showServices();
		}
		else {
			ci.println(getHelp());
		}
	}

	@Override
	public String getHelp()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("\tshowIchManagers - show all ich managers.");
		sb.append("\tshowIchServices [manager name] - show ich services with the manager name.");
		return sb.toString();
	}

}
