/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.external.protocol;


import com.iaspec.rda.plugins.rfid.util.SendType;


/**
 * @author Peter
 */
public class RFIDRequest
{

    private String rootName;
    private String rootTypeValue;
    private String deviceTypeValue;
    private String deviceIdValue;
    private String sessionIdValue;
    private String challengeValue;

    private String sequenceValue;
    private String expectedSequence;

    private String applicationDataStructure;

    private SendType sendType;

    public String getRootName()
    {
        return rootName;
    }

    public void setRootName(String rootName)
    {
        this.rootName = rootName;
    }

    public String getRootTypeValue()
    {
        return rootTypeValue;
    }

    public void setRootTypeValue(String rootTypeValue)
    {
        this.rootTypeValue = rootTypeValue;
    }

    public String getDeviceTypeValue()
    {
        return deviceTypeValue;
    }

    public void setDeviceTypeValue(String deviceTypeValue)
    {
        this.deviceTypeValue = deviceTypeValue;
    }

    public String getDeviceIdValue()
    {
        return deviceIdValue;
    }

    public void setDeviceIdValue(String deviceIdValue)
    {
        this.deviceIdValue = deviceIdValue;
    }

    public String getSessionIdValue()
    {
        return sessionIdValue;
    }

    public void setSessionIdValue(String sessionIdValue)
    {
        this.sessionIdValue = sessionIdValue;
    }

    public String getChallengeValue()
    {
        return challengeValue;
    }

    public void setChallengeValue(String challengeValue)
    {
        this.challengeValue = challengeValue;
    }

    public String getSequenceValue()
    {
        return sequenceValue;
    }

    public void setSequenceValue(String sequenceValue)
    {
        this.sequenceValue = sequenceValue;
    }

    public String getApplicationDataStructure()
    {
        return applicationDataStructure;
    }

    public void setApplicationDataStructure(String applicationDataStructure)
    {
        this.applicationDataStructure = applicationDataStructure;
    }

    public SendType getSendType()
    {
        return sendType;
    }

    public void setSendType(SendType sendType)
    {
        this.sendType = sendType;
    }

    public String getExpectedSequence()
    {
        return expectedSequence;
    }

    public void setExpectedSequence(String expectedSequence)
    {
        this.expectedSequence = expectedSequence;
    }

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("NormalResponse [");
        sb.append("rootName=").append(rootName).append(", ");
        sb.append("rootTypeValue=").append(rootTypeValue).append(", ");
        sb.append("deviceTypeValue=").append(deviceTypeValue).append(", ");
        sb.append("deviceIdValue=").append(deviceIdValue).append(", ");
        sb.append("sessionIdValue=").append(sessionIdValue).append(", ");
        sb.append("sendType=").append(sendType).append(", ");
        sb.append("adChallengeValue=").append(challengeValue).append(", ");
        sb.append("dsSequenceValue=").append(sequenceValue).append(", ");
        sb.append("dsApplicationDataStructure=").append(applicationDataStructure);
        sb.append("]");

        return sb.toString();
    }
}
