package com.roje.game.core.service;

import com.roje.game.core.config.ClientConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpClient;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class ClientService extends Service{
    private NettyTcpClient tcpClient;

    public ClientService(NettyTcpClient tcpClient){
        super(null);
        this.tcpClient = tcpClient;
    }
    public ClientService(ThreadConfig threadConfig,NettyTcpClient tcpClient){
        super(threadConfig);
        this.tcpClient = tcpClient;
    }

}
