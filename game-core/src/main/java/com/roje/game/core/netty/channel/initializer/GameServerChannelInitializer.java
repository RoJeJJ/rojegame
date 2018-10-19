package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.netty.channel.handler.DefaultGameUserInBoundHandler;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;

public class GameServerChannelInitializer extends DefaultChannelInitializer {

    private final NettyServerConfig nettyServerConfig;

    private final Service service;

    private final SessionManager sessionManager;

    private final MessageDispatcher dispatcher;

    public GameServerChannelInitializer(
            NettyServerConfig nettyServerConfig,
            Service service,
            SessionManager sessionManager,
            MessageDispatcher dispatcher) {
        this.nettyServerConfig = nettyServerConfig;
        this.service = service;
        this.sessionManager = sessionManager;
        this.dispatcher = dispatcher;
    }

    @Override
    public void custom(ChannelPipeline pipeline) throws Exception {
        pipeline.addLast(new IdleStateHandler(nettyServerConfig.getReaderIdleTime(),
                nettyServerConfig.getWriterIdleTime(),nettyServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultGameUserInBoundHandler(service,dispatcher,sessionManager));
    }
}
