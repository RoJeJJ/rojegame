package com.roje.game.core.thread.timer;

import com.roje.game.core.thread.ServerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 定时器
 */
public class TimerThread extends Timer {
    private static final Logger LOG = LoggerFactory.getLogger(TimerThread.class);
    /**
     * 定时器依附的线程
     */
    private ServerThread serverThread;
    /**
     * 定时器任务队列
     */
    private Collection<TimerEvent> events = Collections.synchronizedCollection(new ArrayList<>());

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            synchronized (TimerThread.this){
                Iterator<TimerEvent> it = events.iterator();
                while (it.hasNext()){
                    TimerEvent event = it.next();
                    serverThread.execute(event);
                    if (event.getLoop() > 0)
                        event.decrease();
                    if (event.remain() == 0)
                        event.setLoop(0);
                    if (event.getLoop() == 0) {
                        LOG.info("remove");
                        it.remove();
                    }
                }
            }
        }
    };

    public TimerThread(ServerThread serverThread){
        super(serverThread.getThreadName()+"-Timer");
        this.serverThread = serverThread;
        LOG.info("TimerThread:"+serverThread.getThreadName());
    }
    public void start(){
        schedule(task,0,serverThread.getTimeInterval());
    }
    public void stop(boolean flag){
        synchronized (this){
            events.clear();
            if (task != null)
                task.cancel();
            cancel();
        }
        LOG.error("timer:"+serverThread.getThreadName()+" stop flag:"+flag);
    }
    public void addTimerEvent(TimerEvent event){
        synchronized (this){
            events.add(event);
        }
    }
    public void removeTimerEvent(TimerEvent event){
        synchronized (this){
            events.remove(event);
        }
    }

}
