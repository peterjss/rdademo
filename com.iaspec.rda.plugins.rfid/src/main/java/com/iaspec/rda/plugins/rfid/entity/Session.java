/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.entity;


import java.io.Serializable;
import java.util.UUID;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-14
 */
public class Session implements Serializable
{
    private String id;
    private String serverId;
    private String challengeValue;
    private boolean isAlive;
    private boolean isAccessed;
    private String endpoint;
    private String deviceId;
    private String deviceType;

    public static String generateId()
    {
        UUID uuid = UUID.randomUUID();
        return UUID.randomUUID().toString();
    }

    public static String generateChallenge()
    {
        UUID uuid = UUID.randomUUID();
        return UUID.randomUUID().toString();
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getChallengeValue()
    {
        return challengeValue;
    }

    public void setChallengeValue(String challengeValue)
    {
        this.challengeValue = challengeValue;
    }

    public boolean isAlive()
    {
        return isAlive;
    }

    public void setAlive(boolean alive)
    {
        isAlive = alive;
    }

    public String getServerId()
    {
        return serverId;
    }

    public void setServerId(String serverId)
    {
        this.serverId = serverId;
    }

    public boolean isAccessed()
    {
        return isAccessed;
    }

    public void setAccessed(boolean accessed)
    {
        isAccessed = accessed;
    }

    public String getEndpoint()
    {
        return endpoint;
    }

    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
}
