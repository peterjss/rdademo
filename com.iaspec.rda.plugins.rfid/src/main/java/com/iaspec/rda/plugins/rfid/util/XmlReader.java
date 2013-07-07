/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.util;


import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;


/**
 * Created with IntelliJ IDEA. User: Peter Date: 13-1-7 Time: 上午10:35 To change
 * this template use File | Settings | File Templates.
 */
public class XmlReader
{

    // public static SAXReader reader;
    private static EntityResolver aresolve;// 校验器

    // private static Validateor validateor=Validateor.getInstance();

    private static SAXReader validXsd() throws SAXException
    {
        SAXReader reader = new SAXReader();
        if (aresolve == null)
        {
            aresolve = new EntityResolver()
            {
                public InputSource resolveEntity(String arg0, String arg1) throws SAXException, IOException
                {
                    // Resource resource = new
                    // ClassPathResource("com/***/***/***/***/a.xsd");
                    return new InputSource(this.getClass().getResourceAsStream("/RDAMessage.xsd"));
                }
            };
        }
        reader = new SAXReader(true);
        try
        {
            // 符合的标准
            // reader.setFeature("http://apache.org/xml/features/validation/schema",
            // true);
            // reader.setFeature("http://apache.org/xml/features/validation/schema-full-checking",
            // true);
            // reader.setFeature("http://xml.org/sax/features/validation",
            // true);
            reader.setFeature("http://apache.org/xml/features/validation/schema", true);

            reader.setProperty("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema");

        }
        catch (SAXException e1)
        {
            System.err.println("设置校验参数错误");
        }

        reader.setEntityResolver(aresolve);
        return reader;
    }

    public static Document read(File file) throws Exception
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        return doc;
    }

    public static Document read(URL url) throws Exception
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(url);
        return doc;
    }

    public static Document read(String info) throws Exception
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(info.getBytes("UTF-8")));
        return doc;
    }
}
