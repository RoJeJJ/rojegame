package com.roje.game.core.thread.executor;

import com.roje.game.core.config.ExecutorProperties;
import com.roje.game.core.thread.factory.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Slf4j
public class TaskExecutor<T> {
    private List<ScheduledExecutorService> executorServices;

    private final ExecutorProperties properties;

    public TaskExecutor(ExecutorProperties properties) {
        this.properties = properties;
        executorServices = new ArrayList<>();
        init();
    }

    public int threadSize() {
        return properties.getThreadSize();
    }

    public String name() {
        return properties.getName();
    }

    private void init() {
        NamedThreadFactory threadFactory = new NamedThreadFactory(name());
        for (int i = 0; i < threadSize(); i++) {
            executorServices.add(Executors.newSingleThreadScheduledExecutor(threadFactory));
        }
    }

    public ScheduledExecutorService allocateThread(T account) {
        return executorServices.get(account.hashCode() % threadSize());
    }
}
