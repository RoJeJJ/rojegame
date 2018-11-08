package com.roje.game.core.service;

import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.thread.ExecutorPool;
import com.roje.game.core.thread.executor.RoomExecutor;
import com.roje.game.core.thread.executor.TaskExecutor;
import com.roje.game.core.thread.factory.IoThreadFactory;
import com.roje.game.core.thread.ServerThread;
import com.roje.game.core.thread.ThreadType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class Service {
    /**
     * 线程容器
     */
    private final Map<ThreadType,Executor> executorMap = new HashMap<>();

    private final RoomExecutor roomExecutor;

    public Service(ThreadProperties config){
        ThreadProperties.IoConfig ioConf = config.getIoConfig();
        if (ioConf != null) {
            ThreadPoolExecutor ioExecutor = new ThreadPoolExecutor(ioConf.getCorePoolSize(), ioConf.getMaximumPoolSize(),
                    ioConf.getKeepAliveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(ioConf.getCapacity()),
                    new IoThreadFactory());
            executorMap.put(ThreadType.io, ioExecutor);
        }
        ThreadProperties.SyncConfig syncConf = config.getSyncConfig();
        if (syncConf != null) {
            ServerThread syncThread = new ServerThread(new ThreadGroup("sync"),"roje-sync",
                    /*config.getSyncTimeInterval(),*/syncConf.getCommandSize());
            syncThread.start();
            executorMap.put(ThreadType.sync,syncThread);
        }
        if (config.createRoomExecutor){
            roomExecutor = new RoomExecutor(config.getSingleThreadRoomSize());
        }else
            roomExecutor = null;
        Runtime.getRuntime().addShutdownHook(new Thread(this::shutDown));
    }

    private void shutDown(){
        executorMap.values().forEach(executor -> {
            try {
                if (executor instanceof ServerThread) {
                    if (((ServerThread) executor).isAlive())
                        ((ServerThread) executor).shutDown();
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

    @SuppressWarnings("unchecked")
    public <T extends Executor>T getExecutor(ThreadType type){
        return (T) executorMap.get(type);
    }

    public RoomExecutor getRoomExecutor(){
        return roomExecutor;
    }
}
