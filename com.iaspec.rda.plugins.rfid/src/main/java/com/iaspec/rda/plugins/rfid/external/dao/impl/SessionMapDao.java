/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.rfid.external.dao.impl;


import com.iaspec.rda.plugins.rfid.RFIDActivator;
import com.iaspec.rda.plugins.rfid.entity.Session;
import com.iaspec.rda.plugins.rfid.external.dao.ISessionDao;

import java.util.Map;


/**
 * Class description goes here. User: Peter
 *
 * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
 * @version 1.00 13-1-15
 */
public class SessionMapDao implements ISessionDao
{

    private static SessionMapDao instance;

    private SessionMapDao()
    {

    }

    public static SessionMapDao getInstance()
    {
        if (instance == null)
        {
            instance = new SessionMapDao();
        }
        return instance;
    }

    public String createSession(Session session)
    {
        // Session session = new Session();
        Map<String, Session> map = RFIDActivator.sessionMap;

        if (session.getId() == null)
        {
            session.setId(Session.generateId());
        }
        if (map.containsKey(session.getId()))
        {
            Session s = map.get(session.getId());
            return s.getId();
        }
        map.put(session.getId(), session);
        return session.getId();
    }

    public Session getCurrentSession(String sessionId)
    {
        Map<String, Session> map = RFIDActivator.sessionMap;
        return map.get(sessionId);
    }

    public void save(Session session)
    {
        Map<String, Session> map = RFIDActivator.sessionMap;
        map.put(session.getId(), session);
    }

    public void remove(String sessionId)
    {
        Map<String, Session> map = RFIDActivator.sessionMap;
        map.containsKey(sessionId);
        map.remove(sessionId);
    }
}
