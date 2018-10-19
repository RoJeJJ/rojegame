package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "client.cluster")
public class ClusterClientConfig {

    private int soLinger = 0;

    private int readerIdleTime;

    private int writerIdleTime;

    private int allIdleTime;

    private boolean tcpNoDelay;

    private int reconnectTime;
    //连接超时
    private int connectTimeout;

    private String ip;

    private int port;
}
