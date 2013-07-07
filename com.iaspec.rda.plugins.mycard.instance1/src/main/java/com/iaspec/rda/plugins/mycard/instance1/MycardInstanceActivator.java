

package com.iaspec.rda.plugins.mycard.instance1;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import com.iaspec.rda.foundation.message.IMessageDispatcher;
import com.iaspec.rda.foundation.message.impl.bdb.BdbDispatcherFactory;
import com.iaspec.rda.plugins.mycard.instance1.util.MycardBdbDispatcherFactory;
import com.iaspec.rda.plugins.mycard.instance1.util.ServerConfig;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MycardInstanceActivator implements BundleActivator
{
    public static BundleContext context;
    public static IMessageDispatcher messageDispatcher;

    public void start(BundleContext context) throws Exception
    {
    	MycardInstanceActivator.context = context;
        ServerConfig serverConfig = ServerConfig.getInstance();

        messageDispatcher = new MycardBdbDispatcherFactory().createDispatcher();
        messageDispatcher.addMessageQueue(serverConfig.server(),
                BdbDispatcherFactory.BDB_MESSAGE_QUEUE(serverConfig.server()));
        messageDispatcher.start();
    }

    public void stop(BundleContext context) throws Exception
    {
        ServerConfig serverConfig = ServerConfig.getInstance();
        messageDispatcher.removeMessageQueue(serverConfig.server());
        messageDispatcher.stop();
    }

    public static BundleContext getContext()
    {
        return context;
    }

}