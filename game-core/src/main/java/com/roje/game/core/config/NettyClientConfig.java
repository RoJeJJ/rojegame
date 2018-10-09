package com.roje.game.core.config;

import com.roje.game.core.server.ServerType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyClientConfig {

    private int soLinger = 0;

    private int readerIdleTime;

    private int writerIdleTime;

    private int allIdleTime;

    private boolean tcpNoDelay;

    private int reconnectTime;
    //连接超时
    private int connectTimeout;

    private ConnTo toCluster;

    @Getter
    @Setter
    public class ConnTo{
        private ServerType type;
        private String ip;
        private int port;
    }
}
