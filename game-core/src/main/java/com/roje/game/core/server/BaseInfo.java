package com.roje.game.core.server;

import com.roje.game.message.server_info.ServerStatus;
import com.roje.game.message.server_info.ServerType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "server.info")
public class BaseInfo {
    // 服务器ID
    private int id = 0;

    //服务器版本号
    private int versionCode;

    // 服务器名称
    private String name;
    // 地址
    private String ip;
    // 外网地址
    private String url;
    // 端口
    private int innerPort;

    private int userPort;
    // 当前状态 1表示维护；0表示正常
    private ServerStatus state = ServerStatus.Normal;
    // http端口
    private int httpPort;
    // 最大用户人数
    private int maxUserCount;
    // 服务器类型
    private ServerType type;
    //游戏id
    private int gameId;

    private int clientVersionCode;
}
