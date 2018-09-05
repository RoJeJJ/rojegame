package com.roje.game.core.thread.timer.event;

import com.roje.game.core.thread.timer.TimerEvent;
import com.roje.game.core.event.TimerEventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalTime;

public class ServerTimerEvent extends TimerEvent {
    private static final Logger LOG = LoggerFactory.getLogger(ServerTimerEvent.class);
    private TimerEventHandler handler;
    private int sec = -1;
    private int min = -1;
    private int hour = -1;

    public ServerTimerEvent(long end, int loop,TimerEventHandler handler) {
        super(end, loop);
        this.handler = handler;
    }

    public ServerTimerEvent(long end,TimerEventHandler handler) {
        this(end,-1,handler);
    }

    public ServerTimerEvent(TimerEventHandler handler){
        this(0,-1,handler);
    }

    @Override
    public void run() {
        LocalTime time = LocalTime.now();
        int _s = time.getSecond();
        if (sec != _s){
            sec = _s;
            long start = System.currentTimeMillis();
            handler.secondHandler(time);
            long executeTime = System.currentTimeMillis() - start;
            if (executeTime > 30)
                LOG.warn("秒钟定时器执行时间大于30ms:"+executeTime);
        }
        int _m = time.getMinute();
        if (min != _m){
            min = _m;
            long start = System.currentTimeMillis();
            handler.minuteHandler(time);
            long executeTime = System.currentTimeMillis() - start;
            if (executeTime > 30)
                LOG.warn("分钟定时器执行时间大于30ms:"+executeTime);
        }
        int _h = time.getHour();
        if (hour != _h){
            hour = _h;
            long start = System.currentTimeMillis();
            handler.hourHandler(time);
            long executeTime = System.currentTimeMillis() - start;
            if (executeTime > 30)
                LOG.warn("时钟定时器执行时间大于30ms:"+executeTime);
        }
    }
}
