/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.external.dao;


import com.iaspec.rda.plugins.rfid.entity.Session;

/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-15
 */
public interface ISessionDao
{

    public String createSession(Session session);

    public Session getCurrentSession(String sessionId);

    public void save(Session session);
}
