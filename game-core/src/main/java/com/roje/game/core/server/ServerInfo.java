package com.roje.game.core.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;

public class ServerInfo {
    private static final Logger LOG = LoggerFactory.getLogger(ServerInfo.class);
    // 服务器ID
    private int id;

    //服务器版本号
    private int versionCode;

    // 服务器名称
    private String name;
    // 地址
    private String ip;
    // 外网地址
    private String wwwip;
    // 端口
    private int port;
    // 当前状态 1表示维护；0表示正常
    private int state = 0;
    // http端口
    private int httpPort;
    // 最大用户人数
    private int maxUserCount;
    // 在线人数
    private int online;
    // 服务器类型
    private int type;
    // 空闲内存
    private int freeMemory;
    // 可用内存
    private int totalMemory;
    // 版本号,用于判断客户端连接那个服务器
    private int clientVersionCode;

    private transient ChannelHandlerContext channel;

    /** 客户端多连接管理 */
    protected transient Queue<Channel> channels;

    public ChannelHandlerContext getChannelHandlerContext() {
        return channel;
    }

    public void setChannel(ChannelHandlerContext channel) {
        this.channel = channel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getWwwip() {
        return wwwip;
    }

    public void setWwwip(String wwwip) {
        this.wwwip = wwwip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public int getMaxUserCount() {
        return maxUserCount;
    }

    public void setMaxUserCount(int maxUserCount) {
        this.maxUserCount = maxUserCount;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(int freeMemory) {
        this.freeMemory = freeMemory;
    }

    public int getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(int totalMemory) {
        this.totalMemory = totalMemory;
    }

    public int getClientVersionCode() {
        return clientVersionCode;
    }

    public void setClientVersionCode(int clientVersionCode) {
        this.clientVersionCode = clientVersionCode;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }
}
