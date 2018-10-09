package com.roje.game.core.service;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.thread.ExecutorPool;
import com.roje.game.core.thread.IoThreadFactory;
import com.roje.game.core.thread.ServerThread;
import com.roje.game.core.thread.ThreadType;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class Service {
    /**
     * 线程容器
     */
    private final Map<ThreadType,Executor> executorMap = new ConcurrentHashMap<>();

    public Service(ThreadConfig config){
        if (config != null){
            ThreadPoolExecutor ioExecutor = new ThreadPoolExecutor(config.getIoCorePoolSize(),config.getIoMaximumPoolSize(),
                    config.getIoKeepAliveTime(),TimeUnit.MILLISECONDS,new LinkedBlockingQueue<>(config.getIoCapacity()),
                    new IoThreadFactory());
            executorMap.put(ThreadType.io,ioExecutor);
            ServerThread syncThread = new ServerThread(new ThreadGroup(config.getSyncName()),config.getSyncName(),
                    config.getSyncTimeInterval(),config.getSyncCommandSize());
            syncThread.start();
            executorMap.put(ThreadType.sync,syncThread);
        }
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
    }

    private void shutDown(){
        executorMap.values().forEach(executor -> {
            try {
                if (executor instanceof ServerThread) {
                    if (((ServerThread) executor).isAlive())
                        ((ServerThread) executor).stop(true);
                } else if (executor instanceof ThreadPoolExecutor) {
                    if (!((ThreadPoolExecutor) executor).isShutdown()) {
                        ((ThreadPoolExecutor) executor).shutdown();
                        while (!((ThreadPoolExecutor) executor).awaitTermination(5, TimeUnit.SECONDS))
                            log.error("线程池剩余线程:" + ((ThreadPoolExecutor) executor).getActiveCount());
                    }
                } else if (executor instanceof ExecutorPool)
                    ((ExecutorPool) executor).stop();
            }catch (Exception e){
                log.error("关闭线程异常",e);
            }
        });
    }
    /**
     * 添加线程池

     */
    public void addExecutorPool(ThreadType type,Executor executor){
        executorMap.put(type,executor);
    }

    @SuppressWarnings("unchecked")
    public <T extends Executor>T getExecutor(ThreadType type){
        return (T) executorMap.get(type);
    }
}
