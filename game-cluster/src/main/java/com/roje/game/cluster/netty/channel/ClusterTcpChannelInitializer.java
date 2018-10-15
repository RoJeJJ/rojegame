package com.roje.game.cluster.netty.channel;

import com.roje.game.core.config.NettyTcpServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpServerChannelInBoundHandler;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("clusterTcpChannelInitializer")
public class ClusterTcpChannelInitializer extends DefaultChannelInitializer {

    private final NettyTcpServerConfig nettyTcpServerConfig;

    private final Service service;

    private final MessageDispatcher dispatcher;

    private final ServerManager serverManager;

    @Autowired
    public ClusterTcpChannelInitializer(@Qualifier("clusterTcpServerConfig") NettyTcpServerConfig nettyTcpServerConfig,
                                        Service service, MessageDispatcher dispatcher,ServerManager serverManager) {
        this.nettyTcpServerConfig = nettyTcpServerConfig;
        this.service = service;
        this.dispatcher = dispatcher;
        this.serverManager = serverManager;
    }

    @Override
    public void custom(ChannelPipeline pipeline) {
        pipeline.addLast(new IdleStateHandler(nettyTcpServerConfig.getReaderIdleTime(),
                nettyTcpServerConfig.getWriterIdleTime(),
                nettyTcpServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultInnerTcpServerChannelInBoundHandler(false,service,dispatcher,serverManager));
    }
}
