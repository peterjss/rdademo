/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.external.dao.impl;



import java.util.Map;

import com.iaspec.rda.plugins.mycard.MycardActivator;
import com.iaspec.rda.plugins.mycard.entity.Session;
import com.iaspec.rda.plugins.mycard.external.dao.ISessionDao;


/**
 * Class description goes here.
 * User: Peter
 *
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 * @version 1.00 13-3-6
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
        Map<String, Session> map = MycardActivator.sessionMap;

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
        Map<String, Session> map = MycardActivator.sessionMap;
        return map.get(sessionId);
    }

    public void save(Session session)
    {
        Map<String, Session> map = MycardActivator.sessionMap;
        map.put(session.getId(), session);
    }

    public void remove(String sessionId)
    {
        Map<String, Session> map = MycardActivator.sessionMap;
        map.containsKey(sessionId);
        map.remove(sessionId);
    }
}
