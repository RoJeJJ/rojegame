package com.roje.game.core.config;

import com.roje.game.core.server.ServerType;

public class ClientConfig {
    // 客户端线程池大小
    private int orderedThreadPoolExecutorSize = 150;

    private int soLinger = 0;

    private int readerIdleTime;

    private int writerIdleTime;

    private int allIdleTime;

    private boolean tcpNoDelay;

    private int reconnectTime;


    //连接超时
    private int connectTimeout;

    /***
     * 客户端创建的最大连接数
     */
    private int maxConnectCount = 1;

    //连接配置


    // 当前服务器的类型,如当前服务器是gameserver.那么对应ServerType.GameServer = 10
    private ServerType type;

    // 其他配置,如配置服务器允许开启的地图
    private String info;

    private ServerType connToType;

    // 链接到服务器的地址
    private String host="127.0.0.1";

    // 链接到服务器的端口
    private int port=8500;

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getOrderedThreadPoolExecutorSize() {
        return orderedThreadPoolExecutorSize;
    }

    public void setOrderedThreadPoolExecutorSize(int orderedThreadPoolExecutorSize) {
        this.orderedThreadPoolExecutorSize = orderedThreadPoolExecutorSize;
    }

    public int getSoLinger() {
        return soLinger;
    }

    public void setSoLinger(int soLinger) {
        this.soLinger = soLinger;
    }

    public int getMaxConnectCount() {
        return maxConnectCount;
    }

    public void setMaxConnectCount(int maxConnectCount) {
        this.maxConnectCount = maxConnectCount;
    }

    public ServerType getType() {
        return type;
    }

    public void setType(ServerType type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public ServerType getConnToType() {
        return connToType;
    }

    public void setConnToType(ServerType connToType) {
        this.connToType = connToType;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
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

    public int getAllIdleTime() {
        return allIdleTime;
    }

    public void setAllIdleTime(int allIdleTime) {
        this.allIdleTime = allIdleTime;
    }

    @Override
    public String toString() {
        return "ClientConfig:{type:"+type.name()+",host:"+host+",port:"+port+",connType:"+connToType.name()+",connTimeout:"+connectTimeout+"}";
    }

    public boolean isTcpNoDelay() {
        return tcpNoDelay;
    }

    public void setTcpNoDelay(boolean tcpNoDelay) {
        this.tcpNoDelay = tcpNoDelay;
    }

    public int getReconnectTime() {
        return reconnectTime;
    }

    public void setReconnectTime(int reconnectTime) {
        this.reconnectTime = reconnectTime;
    }
}
