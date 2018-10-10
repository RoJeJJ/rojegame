package com.roje.game.core.service;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyClusterTcpClient;

public class ClientService extends Service{
    private NettyClusterTcpClient tcpClient;

    public ClientService(NettyClusterTcpClient tcpClient){
        super(null);
        this.tcpClient = tcpClient;
    }
    public ClientService(ThreadConfig threadConfig,NettyClusterTcpClient tcpClient){
        super(threadConfig);
        this.tcpClient = tcpClient;
    }

}
