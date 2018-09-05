package com.roje.game.core.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NamedThreadFactory implements ThreadFactory {
    private static AtomicInteger counter = new AtomicInteger(1);
    private String name = "Lane";
    private boolean daemon;
    private int priority;
    private ThreadGroup group;

    public NamedThreadFactory(String name) {
        this(name, false, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, boolean daemon) {
        this(name, daemon, Thread.NORM_PRIORITY);
    }

    public NamedThreadFactory(String name, boolean daemon, int priority) {
        this.name = name;
        this.daemon = daemon;
        this.priority = priority;
        SecurityManager sm = System.getSecurityManager();
        group = sm == null?Thread.currentThread().getThreadGroup():sm.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group,r, name + "-" + counter.getAndIncrement(), 0);
        thread.setDaemon(daemon);
        if (priority != -1) {
            thread.setPriority(priority);
        }
        return thread;
    }
}
