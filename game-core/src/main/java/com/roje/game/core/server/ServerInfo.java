package com.roje.game.core.server;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("server-info")
public class ServerInfo {
    private String ip;
    private int port;
    private int gameId;
    private int maxUserCount;
    private String name;
    private int requireVersion;
}
