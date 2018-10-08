package com.roje.game.core.config;


public class ServerConfig {

    //监听的端口;
    private int port;

    // 是否重用地址
    private boolean reuseAddress = true;

    //SO_BACKLOG
    private int soBackLog = 1024;

    // Tcp没有延迟
    private boolean tcpNoDelay = true;

    // 读取空闲时间检测
    private int readerIdleTime = 120;

    // 写入空闲时间检测
    private int writerIdleTime = 120;

    private int allIdleTime = 120;

    private int soLinger = 0;


    //网络带宽：负载均衡时做判断依据。以1M支撑64人并发计算
    private int netSpeed=64*5;

    //限制每秒会话可接受的消息数，超过则关闭
    private int maxCountPerSecond=30;
    //SO_KEEPALIVE
    private boolean soKeepAlive = true;

    private int clientVersionCode;

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setClientVersionCode(int clientVersionCode) {
        this.clientVersionCode = clientVersionCode;
    }

    public int getClientVersionCode() {
        return clientVersionCode;
    }

    public void setSoKeepAlive(boolean soKeepAlive) {
        this.soKeepAlive = soKeepAlive;
    }

    public boolean isSoKeepAlive() {
        return soKeepAlive;
    }

    public void setSoBackLog(int soBackLog) {
        this.soBackLog = soBackLog;
    }

    public int getSoBackLog() {
        return soBackLog;
    }

    public void setAllIdleTime(int allIdleTime) {
        this.allIdleTime = allIdleTime;
    }

    public int getAllIdleTime() {
        return allIdleTime;
    }

    public boolean isReuseAddress() {
        return reuseAddress;
    }

    public void setReuseAddress(boolean reuseAddress) {
        this.reuseAddress = reuseAddress;
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getReaderIdleTime() {
        return readerIdleTime;
    }

    public void setReaderIdleTime(int readerIdleTime) {
        this.readerIdleTime = readerIdleTime;
    }

    public int getWriterIdleTime() {
        return writerIdleTime;
    }

    public void setWriterIdleTime(int writerIdleTime) {
        this.writerIdleTime = writerIdleTime;
    }

    public int getSoLinger() {
        return soLinger;
    }

    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }

    public int getNetSpeed() {
        return netSpeed;
    }

    public void setNetSpeed(int netSpeed) {
        this.netSpeed = netSpeed;
    }

    public int getMaxCountPerSecond() {
        return maxCountPerSecond;
    }

    public void setMaxCountPerSecond(int maxCountPerSecond) {
        this.maxCountPerSecond = maxCountPerSecond;
    }

}
