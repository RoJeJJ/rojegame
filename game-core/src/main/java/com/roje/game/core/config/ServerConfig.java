package com.roje.game.core.config;

import com.roje.game.core.server.ServerType;

public class ServerConfig {
    private int id;
    //服务器名字
    private String name;
    // netty服务器地址
    private String ip;

    //版本号
    private int versionCode;

    // netty端口
    private int port = 8500;


    // http服务器地址
    private String url;

    public int getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    private int maxUserCount;


    // netty服务器线程池大小
    private int orderedThreadPoolExecutorSize = 300;

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

    // 服务器类型
    private ServerType type;

    //http服务器端口
    private int httpPort;

    //网络带宽：负载均衡时做判断依据。以1M支撑64人并发计算
    private int netSpeed=64*5;

    //限制每秒会话可接受的消息数，超过则关闭
    private int maxCountPerSecond=30;
    //SO_KEEPALIVE
    private boolean soKeepAlive = true;
    //要求的客户端版本
    private int clientVersionCode;

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

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getOrderedThreadPoolExecutorSize() {
        return orderedThreadPoolExecutorSize;
    }

    public void setOrderedThreadPoolExecutorSize(int orderedThreadPoolExecutorSize) {
        this.orderedThreadPoolExecutorSize = orderedThreadPoolExecutorSize;
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

    public ServerType getType() {
        return type;
    }

    public void setType(ServerType type) {
        this.type = type;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
