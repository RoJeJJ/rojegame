package com.roje.game.core.thread.factory;

import com.roje.game.core.thread.ThreadType;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class IoThreadFactory implements ThreadFactory {
    private static final AtomicInteger Counter = new AtomicInteger(1);
    private ThreadGroup group;
    public IoThreadFactory(){
        SecurityManager sm = System.getSecurityManager();
        group = sm == null?Thread.currentThread().getThreadGroup():sm.getThreadGroup();
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group,r,"roje-"+ ThreadType.io.name()+"-"+Counter.getAndIncrement(),0);
        if (thread.isDaemon())
            thread.setDaemon(false);
        if (thread.getPriority() != Thread.NORM_PRIORITY)
            thread.setPriority(Thread.NORM_PRIORITY);
        return thread;
    }
}
