/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.util;


import com.iaspec.rda.plugins.rfid.RFIDActivator;
import org.eclipse.core.runtime.FileLocator;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-18
 */
public class ResourceHelper
{

    public static InputStream readResource(String name)
    {
        InputStream is = null;
        try
        {
            URL url = RFIDActivator.getContext().getBundle().getResource(name);
            url = FileLocator.toFileURL(url);
            // is = ResourceHelper.class.getResourceAsStream("/" + name);
            is = new FileInputStream(new File(url.toURI()));

        }
        catch (Exception e)
        {
            is = ResourceHelper.class.getResourceAsStream("/" + name);
        }
        return is;
    }
}
