/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.eclipse.core.runtime.FileLocator;

import com.iaspec.rda.plugins.mycard.MycardActivator;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class ResourceHelper
{

    public static InputStream readResource(String name)
    {
        InputStream is = null;
        try
        {
            URL url = MycardActivator.getContext().getBundle().getResource(name);
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
