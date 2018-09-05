package com.roje.game.core.thread.timer;

/**
 * 定时器,end时间大于0表示截至时间到即销毁；loop为-1标识永久循环
 */
public abstract class TimerEvent implements Runnable {
    private long end;
    private int loop;
    public TimerEvent(long end,int loop){
        this.end = end;
        this.loop = loop;
    }
    public long remain(){
        long cur = System.currentTimeMillis();
        if (end <= cur ){
            if (loop >= 0)
                return 0;
            else
                return -1;
        }else {
            return end - cur;
        }
    }

    public int getLoop() {
        return loop;
    }
    public void decrease(){
        loop--;
    }

    public void setLoop(int loop) {
        this.loop = loop;
    }
}
