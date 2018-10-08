package com.roje.game.gate.server;

import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.Service;


public class GateToGameTcpServer extends Service {

    private NettyTcpServer tcpServer;
    public GateToGameTcpServer(NettyTcpServer tcpServer){
        super(null);
        this.tcpServer = tcpServer;
    }

    @Override
    protected void doShutDown() {
        tcpServer.stop();
    }

    @Override
    protected void onRun() {
        tcpServer.start();
    }
}
