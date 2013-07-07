/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-16
 */
public class PropertiesReader extends Object
{
    private static Properties prop;
    private static String EXCEPTION_PROPERTY = "/exceptions.properties";

    static
    {
        prop = new Properties();
        InputStream in2 = ResourceHelper.readResource(EXCEPTION_PROPERTY);
        // InputStream in2 =
        // this.getClass().getResourceAsStream(EXCEPTION_PROPERTY);
        try
        {
            prop.load(in2);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private PropertiesReader()
    {
    }

    public static String getProperty(String key)
    {
        return prop.getProperty(key).trim();
    }
}
