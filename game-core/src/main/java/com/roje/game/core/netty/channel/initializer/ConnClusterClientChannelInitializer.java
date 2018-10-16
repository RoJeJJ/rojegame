package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpClientChannelInBoundHandler;
import com.roje.game.core.server.BaseInfo;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;


public class ConnClusterClientChannelInitializer extends DefaultChannelInitializer {
    private final NettyConnClusterClientConfig clientConfig;

    private final ISessionManager ISessionManager;

    private final MessageDispatcher dispatcher;

    private final BaseInfo baseInfo;

    public ConnClusterClientChannelInitializer(NettyConnClusterClientConfig clientConfig,
                                               MessageDispatcher dispatcher,
                                               ISessionManager ISessionManager,
                                               BaseInfo baseInfo) {
        this.clientConfig = clientConfig;
        this.dispatcher = dispatcher;
        this.ISessionManager = ISessionManager;
        this.baseInfo = baseInfo;
    }

    @Override
    public void custom(ChannelPipeline pipeline) throws Exception {
        pipeline.addLast(new IdleStateHandler(clientConfig.getReaderIdleTime(),clientConfig.getWriterIdleTime(),clientConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultInnerTcpClientChannelInBoundHandler(null,dispatcher, ISessionManager,baseInfo));
    }
}
