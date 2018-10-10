package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "netty-conn-gate-client")
public class NettyConnGateClientConfig {

    private int soLinger = 0;

    private boolean tcpNoDelay = true;

    //连接超时
    private int connectTimeout = 5;
}
