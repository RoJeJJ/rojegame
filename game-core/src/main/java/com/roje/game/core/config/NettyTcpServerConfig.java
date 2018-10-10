package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties
public class NettyTcpServerConfig extends NettyServerConfig {
    private int readerIdleTime = 0;
    private int writerIdleTime = 0;
    private  int allIdleTime = 0;
}
