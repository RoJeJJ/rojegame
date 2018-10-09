package com.roje.game.core.service;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServerService extends Service {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServerService.class);
    private NettyTcpServer tcpServer;
//    private TimerEvent timerEvent;

    public TcpServerService(ThreadConfig config, NettyTcpServer tcpServer){
        super(config);
        this.tcpServer = tcpServer;
//        this.timerEvent = null;
    }
    public TcpServerService(NettyTcpServer tcpServer){
        this(null,tcpServer);
    }
    public void onShutDown(){}
}
