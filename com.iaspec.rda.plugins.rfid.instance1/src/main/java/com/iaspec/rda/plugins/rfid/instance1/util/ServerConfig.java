/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.instance1.util;


import org.dom4j.DocumentException;
import org.dom4j.Element;

import java.io.File;
import java.util.List;


/**
 * Created with IntelliJ IDEA. User: Peter Date: 13-1-7 Time: 上午10:44 To change
 * this template use File | Settings | File Templates.
 */
public class ServerConfig
{
    private static final String SERVER = "server";
    private static final String HOST = "host";
    private static final String PORT = "port";
    private static final String HANDLER = "handler";
    private static final String SESSIONT_IMEOUT = "sessiontimeout";
    private static final String SSL = "ssl";
    private static final String GZIP = "gzip";

    private static final String XML_PRO = "./settings/";
    private static final String XML_PATH = "./plugin_rfid_instance1_config.xml";
    // private static final XmlReader XML_READER = XmlReader;

    private static ServerConfig SERVER_CONFIG = null;

    private ServerConfig()
    {
        super();
    }

    public String host() throws DocumentException
    {
        return this.rootElement().element(HOST).getTextTrim().trim();
    }

    public int port() throws DocumentException
    {
        return Integer.parseInt(this.rootElement().element(PORT).getTextTrim().trim());
    }

    public boolean ssl() throws DocumentException
    {
        return Integer.parseInt(this.rootElement().element(SSL).getTextTrim().trim()) == 1 ? true : false;
    }

    public boolean gzip() throws DocumentException
    {
        return Integer.parseInt(this.rootElement().element(GZIP).getTextTrim().trim()) == 1 ? true : false;
    }

    public int sessionTimeout() throws DocumentException
    {
        return Integer.valueOf(this.rootElement().element(SESSIONT_IMEOUT).getTextTrim().trim());
    }

    public List<Element> handler() throws DocumentException
    {
        return this.rootElement().elements(HANDLER);
    }

    public String server() throws DocumentException
    {
        return this.rootElement().element(SERVER).getTextTrim().trim();
    }

    private Element rootElement() throws DocumentException
    {
        // URL url =
        // RFIDInstanceActivator.getContext().getBundle().getResource("/");
        //
        // try {
        // url = FileLocator.toFileURL(url);
        // System.out.println("url:" + url);
        // }
        // catch (IOException e) {
        // e.printStackTrace();
        // }

        return XmlReader.read(new File(XML_PATH)).getRootElement();

        // return XML_READER.read(file).getRootElement();
    }

    public static ServerConfig getInstance()
    {
        if (SERVER_CONFIG == null)
        {
            synchronized (ServerConfig.class)
            {
                if (SERVER_CONFIG == null)
                {
                    SERVER_CONFIG = new ServerConfig();
                }
            }
        }

        return SERVER_CONFIG;
    }
}
