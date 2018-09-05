package com.roje.game.core.config;

public class ThreadConfig {
    private int ioCorePoolSize;
    private int ioMaximumPoolSize;
    private int ioKeepAliveTime;
    private int ioCapacity;
    private String syncName;
    private long syncTimeInterval;
    private int syncCommandSize;

    public int getIoCorePoolSize() {
        return ioCorePoolSize;
    }

    public void setIoCorePoolSize(int ioCorePoolSize) {
        this.ioCorePoolSize = ioCorePoolSize;
    }

    public int getIoMaximumPoolSize() {
        return ioMaximumPoolSize;
    }

    public void setIoMaximumPoolSize(int ioMaximumPoolSize) {
        this.ioMaximumPoolSize = ioMaximumPoolSize;
    }

    public int getIoKeepAliveTime() {
        return ioKeepAliveTime;
    }

    public void setIoKeepAliveTime(int ioKeepAliveTime) {
        this.ioKeepAliveTime = ioKeepAliveTime;
    }

    public int getIoCapacity() {
        return ioCapacity;
    }

    public void setIoCapacity(int ioCapacity) {
        this.ioCapacity = ioCapacity;
    }

    public String getSyncName() {
        return syncName;
    }

    public void setSyncName(String syncName) {
        this.syncName = syncName;
    }

    public long getSyncTimeInterval() {
        return syncTimeInterval;
    }

    public void setSyncTimeInterval(long syncTimeInterval) {
        this.syncTimeInterval = syncTimeInterval;
    }

    public int getSyncCommandSize() {
        return syncCommandSize;
    }

    public void setSyncCommandSize(int syncCommandSize) {
        this.syncCommandSize = syncCommandSize;
    }
}
