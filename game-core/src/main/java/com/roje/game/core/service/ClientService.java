package com.roje.game.core.service;

import com.roje.game.core.config.ClientConfig;
import com.roje.game.core.netty.NettyTcpClient;
import io.netty.channel.ChannelHandlerContext;

public class ClientService extends Service{
    private NettyTcpClient tcpClient;
    private ChannelHandlerContext channelHandlerContext;

    public ClientService(NettyTcpClient tcpClient){
        super(null);
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

    public void onChannelOpen(ChannelHandlerContext ctx){}
    public void onChannelClose(ChannelHandlerContext ctx){}

    public void channelOpen(ChannelHandlerContext ctx){
        channelHandlerContext = ctx;
        onChannelOpen(channelHandlerContext);
    }

    public void channelClose(ChannelHandlerContext ctx){
        if (channelHandlerContext == ctx)
            onChannelClose(ctx);
    }

    public void sendMessage(Object msg){
        if (channelHandlerContext != null && channelHandlerContext.channel().isOpen())
            channelHandlerContext.write(msg);
    }

    public NettyTcpClient getTcpClient() {
        return tcpClient;
    }
    public ClientConfig getClientConfig(){
        return tcpClient == null ? null:tcpClient.getClientConfig();
    }
}
