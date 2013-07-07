/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.external.protocol;


import org.dom4j.Namespace;
import org.dom4j.QName;


/**
 */
public interface IXmlElement
{
    public static final Namespace NAMESPACE = new Namespace("ns2", "http://www.iaspec.com/RDAMessage");

    // public static QName ROOT_NAME = new QName("RDA", NAMESPACE);
    public static String NAMESPACE_PREFIX = "ns2";
    public static String NAMESPACE_URL = "http://www.iaspec.com/RDAMessage";

    public static String ROOT_NAME = "RDA";
    public static String ROOT_TYPE = "type";
    public static String ROOT_TYPE_REQ = "REQ";
    public static String ROOT_TYPE_RSP = "RSP";
    public static String SEQUENCE = "sequence";
    public static String VALUE = "value";

    public static QName SEQ_NAME = new QName("SEQ", NAMESPACE);
    public static QName DEVICE_TYPE_NAME = new QName("DEVICE_TYPE", NAMESPACE);
    public static QName DEVICE_ID_NAME = new QName("DEVICE_ID", NAMESPACE);
    public static QName SERVER_ID = new QName("SERVER_ID", NAMESPACE);
    public static QName SESSION_ID = new QName("SESSION_ID", NAMESPACE);
    public static QName CHALLENGE_NAME = new QName("CHALLENGE", NAMESPACE);
    public static QName ERROR_NAME = new QName("ERROR", NAMESPACE);

    public static QName AR_NAME = new QName("AR", NAMESPACE);
    public static QName AC_NAME = new QName("AC", NAMESPACE);
    public static QName AD_NAME = new QName("AD", NAMESPACE);
    public static QName AA_NAME = new QName("AA", NAMESPACE);
    public static QName AN_NAME = new QName("AN", NAMESPACE);

    public static QName DS_NAME = new QName("DS", NAMESPACE);
    public static QName DA_NAME = new QName("DA", NAMESPACE);
    public static QName DN_NAME = new QName("DN", NAMESPACE);
    public static QName DP_NAME = new QName("DP", NAMESPACE);
    public static QName SK_NAME = new QName("SK", NAMESPACE);
    public static QName SA_NAME = new QName("SA", NAMESPACE);
    public static QName SN_NAME = new QName("SN", NAMESPACE);
    public static QName SC_NAME = new QName("SC", NAMESPACE);
    public static QName SS_NAME = new QName("SS", NAMESPACE);

}
