/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */

package com.iaspec.rda.plugins.rfid.util;


import org.jboss.netty.channel.ChannelLocal;

public class DataState
{

    //    public static final ChannelLocal<Session> data = new ChannelLocal<Session>();
    public static final ChannelLocal<Integer> seqData = new ChannelLocal<Integer>();
    private static DataState instance;

    private DataState()
    {

    }

    public static DataState getInstance()
    {
        if (instance == null)
        {
            instance = new DataState();
        }
        return instance;
    }

//    public static boolean isContains(Channel channel, String id) {
//        Session session = data.get(channel);
//        if (id == null && id == "") {
//            return false;
//        }
//
//        if (session != null && session.getId() != null) {
//            return session.getId().equalsIgnoreCase(id);
//        }
//        return false;
//    }
//
//    public static void setData(Channel channel, Session session) {
//        if (session != null) {
//            if (DataState.isContains(channel, session.getId())) {
//                DataState.data.remove(channel);
//            }
//            DataState.data.set(channel, session);
//        }
//    }

//    public static Session getData(Channel channel) {
//        if (channel != null) {
//            return DataState.data.get(channel);
//        }
//        return null;
//    }
}
