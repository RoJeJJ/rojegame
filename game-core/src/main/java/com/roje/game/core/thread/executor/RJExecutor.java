package com.roje.game.core.thread.executor;

import com.roje.game.core.thread.factory.NamedThreadFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class RJExecutor {
    private List<ExecutorService> executorServiceList;

    @Getter
    private int threadSize;

    public RJExecutor(int threadSize, String name){
        executorServiceList = new ArrayList<>();
        if (threadSize > 0){
            NamedThreadFactory threadFactory = new NamedThreadFactory(name);
            this.threadSize = threadSize;
            for (int i=0;i<threadSize;i++)
                executorServiceList.add(Executors.newSingleThreadExecutor(threadFactory));
        }
    }

    public ExecutorService allocateThread(String account){
        if (threadSize <= 0) {
            log.info("没有分配任务线程");
            return null;
        }
        log.info("线程id:{},hash:{}",account.hashCode() % threadSize,account.hashCode());
        return executorServiceList.get(account.hashCode() % threadSize);
    }
}
