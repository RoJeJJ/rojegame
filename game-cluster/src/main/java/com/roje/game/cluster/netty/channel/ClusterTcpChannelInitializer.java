package com.roje.game.cluster.netty.channel;

import com.roje.game.cluster.netty.handler.ClusterTcpServerChannelInBoundHandler;
import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("clusterTcpChannelInitializer")
public class ClusterTcpChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyServerConfig nettyServerConfig;

    private final Service service;

    private final MessageDispatcher dispatcher;

    private final ServerManager serverManager;

    @Autowired
    public ClusterTcpChannelInitializer(@Qualifier("clusterNettyTcpConfig") NettyServerConfig nettyServerConfig,
                                        Service service, MessageDispatcher dispatcher,ServerManager serverManager) {
        this.nettyServerConfig = nettyServerConfig;
        this.service = service;
        this.dispatcher = dispatcher;
        this.serverManager = serverManager;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(nettyServerConfig.getReaderIdleTime(), nettyServerConfig.getWriterIdleTime(), nettyServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageCodec());
        pipeline.addLast(new ClusterTcpServerChannelInBoundHandler(false,service,dispatcher,serverManager));
    }
}
