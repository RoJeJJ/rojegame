package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NettyServerConfig {
    private int soBackLog = 2048;
    private boolean soKeepAlive = true;
    private boolean tcpNoDelay = true;
    private int readerIdleTime = 0;
    private int writerIdleTime = 0;
    private  int allIdleTime = 0;
    private int soLinger = 0;
    private int port = 0;
}
