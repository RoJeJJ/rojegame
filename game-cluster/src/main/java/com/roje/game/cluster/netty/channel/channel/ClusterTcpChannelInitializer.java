package com.roje.game.cluster.netty.channel.channel;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.cluster.netty.channel.handler.DefaultInnerTcpServerChannelInBoundHandler;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("clusterTcpChannelInitializer")
public class ClusterTcpChannelInitializer extends DefaultChannelInitializer {

    private final NettyServerConfig nettyServerConfig;

    private final MessageDispatcher dispatcher;

    private final ServerSessionManager serverManager;

    private final Service service;

    @Autowired
    public ClusterTcpChannelInitializer(
            NettyServerConfig nettyServerConfig,
            MessageDispatcher dispatcher,
            Service service,
            ServerSessionManager serverManager) {
        this.nettyServerConfig = nettyServerConfig;
        this.dispatcher = dispatcher;
        this.service = service;
        this.serverManager = serverManager;
    }

    @Override
    public void custom(ChannelPipeline pipeline) {
        pipeline.addLast(new IdleStateHandler(nettyServerConfig.getReaderIdleTime(),
                nettyServerConfig.getWriterIdleTime(),
                nettyServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultInnerTcpServerChannelInBoundHandler(dispatcher, serverManager,service));
    }
}
