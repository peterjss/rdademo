/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.license;


/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter</a>
 * @version 1.00 13-1-14
 */
public class LicenseRecord
{
    private String customerName;
    private String product;
    private String version;
    private String deviceId;
    private String deviceTypeStr;
    private String connEndpoint;
    private String applProfile;
    private String deviceCert;
    private String serverCert;

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getProduct()
    {
        return product;
    }

    public void setProduct(String product)
    {
        this.product = product;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getDeviceTypeStr()
    {
        return deviceTypeStr;
    }

    public void setDeviceTypeStr(String deviceTypeStr)
    {
        this.deviceTypeStr = deviceTypeStr;
    }

    public String getConnEndpoint()
    {
        return connEndpoint;
    }

    public void setConnEndpoint(String connEndpoint)
    {
        this.connEndpoint = connEndpoint;
    }

    public String getApplProfile()
    {
        return applProfile;
    }

    public void setApplProfile(String applProfile)
    {
        this.applProfile = applProfile;
    }

    public String getDeviceCert()
    {
        return deviceCert;
    }

    public void setDeviceCert(String deviceCert)
    {
        this.deviceCert = deviceCert;
    }

    public String getServerCert()
    {
        return serverCert;
    }

    public void setServerCert(String serverCert)
    {
        this.serverCert = serverCert;
    }
}
