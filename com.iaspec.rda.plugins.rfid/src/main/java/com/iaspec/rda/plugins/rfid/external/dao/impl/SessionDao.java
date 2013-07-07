///*
// * Copyright (c) 2012 iASPEC. All Rights Reserved.
// */
//
//package com.iaspec.rda.plugins.rfid.external.dao.impl;
//
//import com.iaspec.rda.plugins.rfid.RFIDActivator;
//import com.iaspec.rda.plugins.rfid.entity.Session;
//import com.iaspec.rda.plugins.rfid.external.dao.BdbPersistenceStore;
//import com.iaspec.rda.plugins.rfid.external.dao.ISessionDao;
//
///**
// * Class description goes here.
// * User: Peter
// *
// * @author <a href="mailto:yang.li@iaspec.com">Peter Li</a>
// * @version 1.00 13-1-14
// */
//public class SessionDao implements ISessionDao {
//    private BdbPersistenceStore<Session> db = BdbPersistenceStore.createBdbPersistenceStore(Session.class);
//    private static SessionDao instance;
//
//    private SessionDao() {
//
//    }
//
//    public static SessionDao getInstance() {
//        if (instance == null) {
//            instance = new SessionDao();
//            instance.db.init();
//            instance.db.setBdbStorePath("RDA_RFID_DB");
//        }
//        return instance;
//    }
//
//    public String createSession(Session session) {
////        Session session = new Session();
//        db.init();
//        String key = db.write(session);
//        db.close();
//        return key;
////        session.setId(Session.generateId());
////        DataState.setData(channel, session);
////        return session;
//    }
//
//    public Session getCurrentSession(String sessionId) {
//        db.init();
//        Session session = db.read(sessionId);
//        db.close();
//
//        return session;
//    }
//
//    public void save(Session session) {
//        db.init();
//        db.write(session);
//        db.close();
//    }
//}
