/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.util;


import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iaspec.rda.plugins.mycard.external.protocol.GPSRequest;


/**
 * Class description goes here.
 *
 * @version 1.00 2013-2-28
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 */

public class MessageUtil
{
    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    public static GPSRequest decodeMessage(byte[] messageContents) throws Exception
    {
        if (messageContents.length < 8)
        {
            return null;
        }
        GPSRequest request = new GPSRequest();
        ByteBuffer byteBuffer = ByteBuffer.wrap(messageContents);

        try
        {
        	 byteBuffer.getInt();
             request.setMessageHeader(byteBuffer.get());
             request.setReservedBit(byteBuffer.get());
             request.setDeviceType(byteBuffer.get());
             request.setProtocolNumber(byteBuffer.get());
             request.setOption(byteBuffer.get());
             request.setMessageLength(byteBuffer.getShort());
             byte[] messageContent =new byte[request.getMessageLength()];
             byteBuffer.get(messageContent);
             request.setMessageContent(messageContent);
             request.setChecksum(byteBuffer.getShort());
             request.setMessageTail(byteBuffer.get());
             byteBuffer.flip();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            logger.error("Server side Unexpected exception from downstream.", e.getCause());
            return null;
        }

        return request;
    }
}
