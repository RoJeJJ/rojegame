package com.roje.game.core.service;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.thread.ExecutorPool;
import com.roje.game.core.thread.IoThreadFactory;
import com.roje.game.core.thread.ServerThread;
import com.roje.game.core.thread.ThreadType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.*;

public abstract class Service implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Service.class);
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
            executorMap.put(ThreadType.sync,syncThread);
        }
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
                            LOG.error("线程池剩余线程:" + ((ThreadPoolExecutor) executor).getActiveCount());
                    }
                } else if (executor instanceof ExecutorPool)
                    ((ExecutorPool) executor).stop();
            }catch (Exception e){
                LOG.error("关闭线程异常",e);
            }
        });
        doShutDown();
    }

    /**
     * 关闭
     */
    protected abstract void doShutDown();

    protected abstract void onRun();
    /**
     * 添加房间线程池
     * @param executor 房间线程池
     */
    public void addRoomExecutor(Executor executor){
        executorMap.put(ThreadType.room,executor);
    }

    @Override
    public void run() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
        onRun();
    }

    @SuppressWarnings("unchecked")
    public <T extends Executor>T getExecutor(ThreadType type){
        return (T) executorMap.get(type);
    }
}
