package com.roje.game.core.server;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfo {
    private String ip; // ip地址
    private int port;
    private int gameId;
    private int online; // 在线人数
    private int maxUserCount; // 最大在线人数
    private String name; //名字
    private int requireVersion;//要求的客户端版本
}
