package com.roje.game.core.server;

import com.google.protobuf.Message;
import com.roje.game.message.login.LoginMessage;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Getter
@Setter
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
    private int userPort;

    private int innerPort;
    // 当前状态 1表示维护；0表示正常
    private int state = 0;
    // http端口
    private int httpPort;
    // 最大用户人数
    private int maxUserCount;
    // 在线人数
    private int online;
    //连接人数
    private int connectedCount;
    // 服务器类型
    private int type;
    // 空闲内存
    private int freeMemory;
    // 可用内存
    private int totalMemory;
    // 版本号,用于判断客户端连接那个服务器
    private int clientVersionCode;

    private transient Channel channel;

    public void send(Message message) {
        if (channel != null && channel.isActive())
            channel.writeAndFlush(message);
    }
}
