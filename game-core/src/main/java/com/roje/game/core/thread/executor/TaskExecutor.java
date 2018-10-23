package com.roje.game.core.thread.executor;

import com.roje.game.core.thread.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class TaskExecutor {
    private List<ScheduledExecutorService> executorServices;

    private String name;

    private int threadSize;

    public TaskExecutor(String name,int threadSize) {
        this.name = name;
        this.threadSize = threadSize;
        executorServices = new ArrayList<>();
        init();
        log.info("任务线程{name:{},thread-size:{}}",name,threadSize);
    }

    public int threadSize() {
        return threadSize;
    }

    public String name() {
        return name;
    }

    private void init() {
        NamedThreadFactory threadFactory = new NamedThreadFactory(name);
        for (int i = 0; i < threadSize(); i++) {
            executorServices.add(Executors.newSingleThreadScheduledExecutor(threadFactory));
        }
    }

    public ScheduledExecutorService allocateThread(Object channelId) {
        return executorServices.get(channelId.hashCode() % threadSize);
    }

    public void shutDown(){
        executorServices.forEach(ExecutorService::shutdown);
    }
}
