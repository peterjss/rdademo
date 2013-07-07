/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.instance1.util;


import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;


/**
 * Class description goes here.
 *
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 * @version 1.00 2013-3-6
 */

public class XmlReader
{

    public static Document read(File file) throws DocumentException
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(file);
        return doc;
    }

    public static Document read(URL url) throws DocumentException
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(url);
        return doc;
    }

    public static Document read(String info) throws DocumentException, UnsupportedEncodingException
    {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(new ByteArrayInputStream(info.getBytes("UTF-8")));
        return doc;
    }
}
