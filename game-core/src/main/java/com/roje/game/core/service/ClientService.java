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

    @Override
    protected void onRun() {
        tcpClient.start();
    }

    @Override
    protected void doShutDown() {
        tcpClient.shutDown();
    }

    public NettyTcpClient getTcpClient() {
        return tcpClient;
    }
    public ClientConfig getClientConfig(){
        return tcpClient == null ? null:tcpClient.getClientConfig();
    }
}
