/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.mycard.external.dao;

import com.iaspec.rda.plugins.mycard.entity.Session;


/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 * @version 1.00 13-3-6
 */

public interface ISessionDao
{

    public String createSession(Session session);

    public Session getCurrentSession(String sessionId);

    public void save(Session session);
}
