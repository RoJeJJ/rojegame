package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties
public class NettyServerConfig {
    private int soBackLog = 2048;
    private boolean soKeepAlive = true;
    private boolean tcpNoDelay = true;
    private int soLinger = 0;
}
