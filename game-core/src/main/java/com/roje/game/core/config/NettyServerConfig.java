package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("game-server.config")
public class NettyServerConfig {
    private int port;
    private int soBackLog = 2048;
    private boolean soKeepAlive = true;
    private boolean tcpNoDelay = true;
    private int soLinger = 0;
    private int readerIdleTime = 0;
    private int writerIdleTime = 0;
    private  int allIdleTime = 0;
}
