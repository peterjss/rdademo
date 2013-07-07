/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.util;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class ExceptionMessages
{

    // System unexpected exception.
    public static final String EXCEPTION_SYSTEM = "NAK000";

    // Invalid decrypted challenge.
    public static final String EXCEPTION_INVALID_DECRYPTED_CHALLENGE = "NAK001";

    // Invalid license file or the license file is expired.
    public static final String EXCEPTION_INVALID_LICENSE = "NAK002";

    // Invalid device type, device ID or connection end-point
    public static final String EXCEPTION_INVALID_DEVICE_OR_CONNECTION = "NAK003";

    // Session Timeout/Not exist
    public static final String EXCEPTION_INVALID_SESSION = "NAK004";

    // Invalid message format
    public static final String EXCEPTION_INVALID_MESSAGE_FORMAT = "NAK005";

    // Invalid Sequence Number
    public static final String EXCEPTION_INVALID_SEQUENCE_NUMBER = "NAK006";

    // Service un-available.
    public static final String EXCEPTION_SERVICE_UNAVAILABLE = "NAK007";

    // Forced Logoff
    public static final String EXCEPTION_FORCED_LOGOFF = "NAK008";

    // Certificate is revoked.
    public static final String EXCEPTION_CERTIFICATE_IS_REVOKED = "NAK009";

    public static String[] getExceptionMessage(String key)
    {
        String msg = PropertiesReader.getProperty(key);
        String realKey = key;

        if (msg == null)
        {
            msg = PropertiesReader.getProperty(EXCEPTION_SYSTEM);
            realKey = EXCEPTION_SYSTEM;
        }

        return new String[]{realKey, msg};
    }
}
