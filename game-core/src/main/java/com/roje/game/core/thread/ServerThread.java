package com.roje.game.core.thread;

import com.roje.game.core.thread.timer.TimerEvent;
import com.roje.game.core.thread.timer.TimerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 服务器线程模型
 */
public class ServerThread extends Thread implements Executor {
    private static final Logger LOG = LoggerFactory.getLogger(ServerThread.class);
    /**
     * 线程名称
     */
    private String name;
    /**
     * 定时任务时间间隔
     */
    private final long timeInterval;
    /**
     * 任务队列
     */
    private LinkedBlockingQueue<Runnable> queue;
    /**
     * 已经暂停
     */
    protected boolean stop;
    /**
     * 最后一次执行任务的时间
     */
    private long lastExecuteTime;
    /**
     * 定时器
     */
    private TimerThread timer;

    public ServerThread(ThreadGroup group,String threadName,long timeInterval,int commandSize){
        super(group,threadName);
        this.name = threadName;
        this.timeInterval = timeInterval;
        if (timeInterval > 0)
            timer = new TimerThread(this);
        queue = new LinkedBlockingQueue<>(commandSize);
        setUncaughtExceptionHandler((t, e) -> {
            LOG.error("ServerThread.setUncaughtExceptionHandler",e);
            if (timer != null)
                timer.stop(true);
        });
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
    public void addTimerEvent(TimerEvent event){
        if (timer != null)
            timer.addTimerEvent(event);
    }

    public long getTimeInterval() {
        return timeInterval;
    }

    public long getLastExecuteTime() {
        return lastExecuteTime;
    }

    @Override
    public void run() {
        stop = false;
        if (timeInterval > 0 && timer != null)
            timer.start();
        int loop = 0;
        while (!stop && !isInterrupted()){
            Runnable command = queue.poll();
            if (command == null){
                synchronized (this){
                    loop = 0;
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        LOG.error("ServerThread",e);
                    }
                }
            }else {
                loop++;
                lastExecuteTime = System.currentTimeMillis();
                try {
                    command.run();
                }catch (Exception e){
                    LOG.error("执行"+name+"任务出现异常",e);
                }
                long cost = System.currentTimeMillis() - lastExecuteTime;
                if (cost > 30L)
                    LOG.warn("线程：{} 执行 {} 消耗时间过长 {}毫秒,当前命令数 {} 条", name, command.getClass().getSimpleName(), cost,
                            queue.size());
                if (loop > 300){
                    loop = 0;
                    LOG.warn("线程：{} 已循环执行{} 次,当前命令数{}", name, loop, queue.size());
                }
            }
        }
    }
    public void stop(boolean flag){
        stop = false;
        LOG.warn("线程{}停止",name);
        if (timer != null)
            timer.stop(flag);
        queue.clear();
        synchronized (this){
            notify();
            interrupt();
        }
    }
}
