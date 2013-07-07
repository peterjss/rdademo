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
public interface ILogProxy
{
    public void createProxy() throws Exception;

    public void shutdown();
}
