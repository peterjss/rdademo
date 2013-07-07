
/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.external.protocol;


/**
 * @author Peter
 */
public class RFIDResponse
{
    private String rootName;
    private String rootTypeValue;
    private String serverIdValue;
    private String sessionIdValue;

    private String challengeValue;

    private String errorValue;
    private String errorDescription;

    private String sequenceValue;

//    private String dnSequenceValue;
//    private String dnErrorValue;
//    private String dnErrorDescription;
//    private String sequenceValue;

    //    private String dpSequenceValue;
    private String applicationDataStructure;

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

    public String getServerIdValue()
    {
        return serverIdValue;
    }

    public void setServerIdValue(String serverIdValue)
    {
        this.serverIdValue = serverIdValue;
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

    public String getErrorValue()
    {
        return errorValue;
    }

    public void setErrorValue(String errorValue)
    {
        this.errorValue = errorValue;
    }

    public String getErrorDescription()
    {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription)
    {
        this.errorDescription = errorDescription;
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

    @Override
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("NormalResponse [");
        sb.append("rootName=").append(rootName).append(", ");
        sb.append("rootTypeValue=").append(rootTypeValue).append(", ");
        sb.append("serverIdValue=").append(serverIdValue).append(", ");
        sb.append("sessionIdValue=").append(sessionIdValue).append(", ");
        sb.append("challengeValue=").append(challengeValue).append(", ");
        sb.append("errorDescription=").append(errorDescription).append(", ");
//        sb.append("daSequenceValue = ").append(daSequenceValue).append(",");
        sb.append("sequenceValue=").append(sequenceValue).append(", ");
//        sb.append("dnErrorValue = ").append(dnErrorValue).append(",");
//        sb.append("dnErrorDescription = ").append(dnErrorDescription).append(",");
//        sb.append("dnSeqSequenceValue = ").append(dnSeqSequenceValue).append(",");
//        sb.append("dpSequenceValue = ").append(dpSequenceValue).append(",");
        sb.append("applicationDataStructure=").append(applicationDataStructure);

        sb.append("]");

        return sb.toString();
    }
}