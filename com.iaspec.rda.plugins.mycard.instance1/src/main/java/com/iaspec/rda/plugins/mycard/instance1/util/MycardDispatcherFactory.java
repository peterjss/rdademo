/*
 * Copyright (c) 2012 iASPEC. All Rights Reserved.
 */


package com.iaspec.rda.plugins.mycard.instance1.util;

import com.iaspec.rda.foundation.message.ExecutorConfig;
import com.iaspec.rda.foundation.message.IMessageWorker;
import com.iaspec.rda.foundation.message.impl.memory.MemoryDispatcherFactory;



/**
 * Class description goes here.
 *
 * @author <a href="mailto:li.zhou@iaspec.com">Julia</a>
 * @version 1.00 2013-3-6
 */

public class MycardDispatcherFactory extends MemoryDispatcherFactory
{

    @Override
    public IMessageWorker getMessageWorker()
    {
        MycardMessageWorker worker = new MycardMessageWorker();
        return worker;
    }

    @Override
    public ExecutorConfig getExecutorConfig()
    {
        ExecutorConfig config = new ExecutorConfig();
        config.setCorePoolSize(3);
        config.setMaximumPoolSize(10);
        config.setKeepAliveSecond(200);

        return config;
    }

}
