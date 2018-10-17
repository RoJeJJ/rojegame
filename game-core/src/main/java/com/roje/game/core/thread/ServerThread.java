package com.roje.game.core.thread;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 服务器线程模型
 */
@Slf4j
public class ServerThread extends Thread implements Executor {
    /**
     * 线程名称
     */
    private String name;

//    /**
//     * 定时任务时间间隔
//     */
//    private final long timeInterval;
    /**
     * 任务队列
     */
    private LinkedBlockingQueue<Runnable> queue;
    /**
     * 已经暂停
     */
    private boolean stop;
    /**
     * 最后一次执行任务的时间
     */
    private long lastExecuteTime;
    /**
     * 定时器
     */

    public ServerThread(ThreadGroup group,String threadName,/*long timeInterval,*/int commandSize){
        super(group,threadName);
        this.name = threadName;
        /*this.timeInterval = timeInterval;*/
        queue = new LinkedBlockingQueue<>(commandSize);
        setUncaughtExceptionHandler((t, e) -> log.error("ServerThread.setUncaughtExceptionHandler",e));
    }
    @Override
    public void execute(Runnable command) {
        if (queue.contains(command))
            return;
        queue.add(command);
        synchronized (this) {
            notify();
        }
    }
    public String getThreadName(){
        return name;
    }

//    public long getTimeInterval() {
//        return timeInterval;
//    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    @Override
    public void run() {
        stop = false;
        log.info(name+"线程已经开始运行");
        int loop = 0;
        while (!stop && !isInterrupted()){
            Runnable command = queue.poll();
            if (command == null){
                synchronized (this){
                    loop = 0;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        log.error("ServerThread",e);
                    }
                }
            }else {
                loop++;
                lastExecuteTime = System.currentTimeMillis();
                try {
                    command.run();
                }catch (Exception e){
                    log.error("执行"+name+"任务出现异常",e);
                }
                long cost = System.currentTimeMillis() - lastExecuteTime;
                if (cost > 30L)
                    log.warn("线程：{} 执行 {} 消耗时间过长 {}毫秒,当前命令数 {} 条", name, command.getClass().getSimpleName(), cost,
                            queue.size());
                if (loop > 300){
                    loop = 0;
                    log.warn("线程：{} 已循环执行{} 次,当前命令数{}", name, loop, queue.size());
                }
            }
        }
    }
    public void shutDown(){
        stop = false;
        log.warn("线程{}停止",name);
        queue.clear();
        synchronized (this){
            notify();
            interrupt();
        }
    }
}
