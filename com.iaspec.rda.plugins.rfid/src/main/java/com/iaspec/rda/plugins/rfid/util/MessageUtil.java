/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.util;


import com.iaspec.rda.plugins.rfid.external.protocol.IXmlElement;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDRequest;
import com.iaspec.rda.plugins.rfid.external.protocol.RFIDResponse;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-15
 */
public class MessageUtil
{
    private static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);

    public static RFIDRequest decodeMessage(ChannelBuffer buffer) throws Exception
    {
        if (buffer.readableBytes() < 53)
        {
            return null;
        }

        buffer.markReaderIndex();
        RFIDRequest request = new RFIDRequest();

        byte[] info = new byte[buffer.readableBytes()];
        buffer.readBytes(info);
        String xmlStr = new String(info);
        logger.debug("Received messsge is format as {}", xmlStr);
        // xmlStr =
        // "<?xml version='1.0' encoding='UTF-8' standalone='yes'?><RDA xmlns:ns2='http://www.iaspec.com/RDAMessage' type='REQ'><DEVICE_TYPE value='rfid'/><DEVICE_ID value='0000000100000001bG7Ohf7P8i15E5Ie'/><ns2:AR/></RDA>";
        try
        {
            Document document = XmlReader.read(xmlStr);
            // document = DocumentHelper.parseText(xmlStr);
            Element rda = document.getRootElement();// .ELEMENT(IXmlElement.ROOT_NAME);

            String rootTypeValue = rda.attribute(IXmlElement.ROOT_TYPE).getValue();
            if (!IXmlElement.ROOT_TYPE_REQ.equalsIgnoreCase(rootTypeValue))
            {
                buffer.resetReaderIndex();
                return null;
            }
            request.setSendType(getSendTypeFromRootElement(rda));
            String deviceTypeValue = rda.element(IXmlElement.DEVICE_TYPE_NAME).attribute(IXmlElement.VALUE).getValue();
            String deviceTypeIdValue = rda.element(IXmlElement.DEVICE_ID_NAME).attribute(IXmlElement.VALUE).getValue();

            Element session = rda.element(IXmlElement.SESSION_ID);
            if (session != null)
            {
                String sessionIdValue = rda.element(IXmlElement.SESSION_ID).attributeValue(IXmlElement.VALUE);
                request.setSessionIdValue(sessionIdValue);
            }
            if (request.getSendType().equals(SendType.AD))
            {
                String challengeValue = rda.element(IXmlElement.AD_NAME).element(IXmlElement.CHALLENGE_NAME)
                        .attributeValue(IXmlElement.VALUE);
                request.setChallengeValue(challengeValue);
            }
            else if (request.getSendType().equals(SendType.DS))
            {
                Element ds = rda.element(IXmlElement.DS_NAME);
                String dsSeq = ds.attributeValue(IXmlElement.SEQUENCE);
                request.setSequenceValue(dsSeq);

                request.setApplicationDataStructure(ds.element("data").getTextTrim());
            }

            request.setRootName(IXmlElement.ROOT_NAME);
            request.setRootTypeValue(rootTypeValue);
            request.setDeviceTypeValue(deviceTypeValue);
            request.setDeviceIdValue(deviceTypeIdValue);
        }
        catch (Exception e)
        {
            logger.error("Server side Unexpected exception from downstream.", e.getCause());
            buffer.resetReaderIndex();
            return null;
        }
        finally
        {
            buffer.resetReaderIndex();
        }

        return request;
    }

    private static SendType getSendTypeFromRootElement(Element rda)
    {
        if (rda.element(IXmlElement.AR_NAME) != null)
        {
            return SendType.AR;
        }
        else if (rda.element(IXmlElement.AC_NAME) != null)
        {
            return SendType.AC;
        }
        else if (rda.element(IXmlElement.AD_NAME) != null)
        {
            return SendType.AD;
        }
        else if (rda.element(IXmlElement.AA_NAME) != null)
        {
            return SendType.AA;
        }
        else if (rda.element(IXmlElement.DS_NAME) != null)
        {
            return SendType.DS;
        }
        else if (rda.element(IXmlElement.DA_NAME) != null)
        {
            return SendType.DA;
        }
        else if (rda.element(IXmlElement.DN_NAME) != null)
        {
            return SendType.DN;
        }
        else if (rda.element(IXmlElement.DP_NAME) != null)
        {
            return SendType.DP;
        }
        else if (rda.element(IXmlElement.SK_NAME) != null)
        {
            return SendType.SK;
        }
        else if (rda.element(IXmlElement.SA_NAME) != null)
        {
            return SendType.SA;
        }
        else if (rda.element(IXmlElement.SN_NAME) != null)
        {
            return SendType.SN;
        }
        else if (rda.element(IXmlElement.SC_NAME) != null)
        {
            return SendType.SC;
        }
        else if (rda.element(IXmlElement.SS_NAME) != null)
        {
            return SendType.SS;
        }
        return null;
    }

    public static Object acEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();
        // DOMDocument document = new DOMDocument();
        // document.setXmlStandalone(true);
        // Element rootElement =
        // document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.ROOT_NAMESPACE.getURI());
        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        // Element rootElement = new
        // DOMElement(IXmlElement.ROOT_NAME,
        // IXmlElement.ROOT_NAMESPACE.getURI());
        // rootElement.set(true);
        // rootElement.setDocumentURI(IXmlElement.ROOT_NAMESPACE.getURI());
        // document.add(rootElement);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());
        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());

        Element acElement = rootElement.addElement(IXmlElement.AC_NAME);
        acElement.addElement(IXmlElement.CHALLENGE_NAME).addAttribute(IXmlElement.VALUE, response.getChallengeValue());

        String xml = document.asXML();
        logger.debug("Format the AC response as {} ", xml);
        // String xml =
        // "<RDA xmlns:ns2='http://www.iaspec.com/RDAMessage' type='RSP'><SESSION_ID value='dc23844e-96e6-4f33-ac1a-19cf6bd6d355'/><SERVER_ID value='RDA_RFID_P1'/><ns2:AC><CHALLENGE value='054d0865-de91-4965-a74f-ab128ea3dca5'/></ns2:AC></RDA>";
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    public static Object aaEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        // Element rootElement =
        // document.addElement(IXmlElement.ROOT_NAME);
        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        rootElement.addElement(IXmlElement.AA_NAME);

        String xml = document.asXML();
        logger.debug("Format the AA response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    /**
     * <RDA type=’RSP’> <SERVER_ID value=’[rda_server_id]’/> <SESSION_ID
     * value=’[session string]’/> <AN> <ERROR value=’[error code]’>[Error
     * Description]</ERROR> </AN> </RDA>
     */
    public static Object anEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        // document.add(element)
        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());
        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());

        Element anElement = rootElement.addElement(IXmlElement.AN_NAME);
        Element errElement = anElement.addElement(IXmlElement.ERROR_NAME);
        errElement.addAttribute(IXmlElement.VALUE, response.getErrorValue());
        errElement.setText(response.getErrorDescription());

        String xml = document.asXML();
        logger.debug("Format the AN response as {} ", xml);
        // String xml =
        // "<RDA xmlns:ns2='http://www.iaspec.com/RDAMessage' type='RSP'><SESSION_ID value='dc23844e-96e6-4f33-ac1a-19cf6bd6d355'/><SERVER_ID value='RDA_RFID_P1'/><ns2:AC><CHALLENGE value='054d0865-de91-4965-a74f-ab128ea3dca5'/></ns2:AC></RDA>";
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    public static Object daEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        rootElement.addElement(IXmlElement.DA_NAME).addAttribute(IXmlElement.SEQUENCE, response.getSequenceValue());

        String xml = document.asXML();
        logger.debug("Format the DA response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    /**
     * <RDA type=’RSP’> <SERVER_ID value=’[rda_server_id]’/> <SESSION_ID
     * value=’[session string]’/> <DN sequence=’[sequence no.]’> <ERROR
     * value=’[error code]’>[Error Description]</ERROR> <SEQ sequence=’[sequence
     * no.]’> </DN> </RDA>
     */
    public static Object dnEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        Element dnElement = rootElement.addElement(IXmlElement.DN_NAME);
        dnElement.addAttribute(IXmlElement.SEQUENCE, response.getSequenceValue());

        Element errElement = dnElement.addElement(IXmlElement.ERROR_NAME);
        errElement.addAttribute(IXmlElement.VALUE, response.getErrorValue());
        errElement.setText(response.getErrorDescription());

        dnElement.addElement(IXmlElement.SEQ_NAME).addAttribute(IXmlElement.SEQUENCE, response.getSequenceValue());

        String xml = document.asXML();
        logger.debug("Format the DN response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    public static Object saEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        rootElement.addElement(IXmlElement.SA_NAME);

        String xml = document.asXML();
        logger.debug("Format the SA response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    /**
     * <RDA type=’RSP’> <SERVER_ID value=’[rda_server_id]’/> <SESSION_ID
     * value=’[session string]’/> <SN> <ERROR value=’[error code]’>[Error
     * Description]</ERROR> </SN> </RDA>
     */
    public static Object snEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        Element snElement = rootElement.addElement(IXmlElement.SN_NAME);

        Element errElement = snElement.addElement(IXmlElement.ERROR_NAME);
        errElement.addAttribute(IXmlElement.VALUE, response.getErrorValue());
        errElement.setText(response.getErrorDescription());

        String xml = document.asXML();
        logger.debug("Format the SN response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }

    /**
     * <RDA type=’RSP’> <SERVER_ID value=’[rda_server_id]’/> <SESSION_ID
     * value=’[session string]’/> <SS> <ERROR value=’[error code]’>[Error
     * Description]</ERROR> </SS> </RDA>
     */
    public static Object ssEcodeMessage(Object msg)
    {
        if (!(msg instanceof RFIDResponse))
        {
            return msg;
        }

        RFIDResponse response = (RFIDResponse) msg;

        ChannelBuffer dataBuffer = ChannelBuffers.dynamicBuffer();
        Document document = DocumentHelper.createDocument();

        Element rootElement = document.addElement(IXmlElement.ROOT_NAME);
        // Element rootElement = document.addElement(IXmlElement.ROOT_NAME,
        // IXmlElement.NAMESPACE.getURI());
        rootElement.addNamespace(IXmlElement.NAMESPACE_PREFIX, IXmlElement.NAMESPACE_URL);

        rootElement.addAttribute(IXmlElement.ROOT_TYPE, IXmlElement.ROOT_TYPE_RSP);

        rootElement.addElement(IXmlElement.SERVER_ID).addAttribute(IXmlElement.VALUE, response.getServerIdValue());
        rootElement.addElement(IXmlElement.SESSION_ID).addAttribute(IXmlElement.VALUE, response.getSessionIdValue());

        Element ssElement = rootElement.addElement(IXmlElement.SS_NAME);

        if (response.getErrorValue() != null && response.getErrorDescription() != null)
        {
            Element errElement = ssElement.addElement(IXmlElement.ERROR_NAME);
            errElement.addAttribute(IXmlElement.VALUE, response.getErrorValue());
            errElement.setText(response.getErrorDescription());
        }
        String xml = document.asXML();
        logger.debug("Format the SS response as {} ", xml);
        dataBuffer.writeBytes(xml.getBytes());

        return dataBuffer;
    }
}
