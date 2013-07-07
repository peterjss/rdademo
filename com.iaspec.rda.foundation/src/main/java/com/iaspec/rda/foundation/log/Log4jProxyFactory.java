package com.iaspec.rda.foundation.log;


import com.iaspec.rda.foundation.utils.ResourceHelper;
import org.apache.log4j.LogManager;
import org.apache.log4j.PropertyConfigurator;

import java.io.InputStream;
import java.util.Properties;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-3-5
 */
public class Log4jProxyFactory implements ILogProxy
{
    private static Log4jProxyFactory logProxy;

    private Log4jProxyFactory(){

    }

    public static Log4jProxyFactory getInstance(){
        if(logProxy == null){
            logProxy = new Log4jProxyFactory();
        }
        return logProxy;
    }

    public void createProxy() throws Exception{
        Properties props = new Properties();
        InputStream is = ResourceHelper.readResource("log4j.properties");
        props.load(is);

        PropertyConfigurator.configure(props);
    }

    public void shutdown(){
        LogManager.shutdown();
    }
}
