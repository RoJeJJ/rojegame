package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.ClusterClientConfig;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpClientChannelInBoundHandler;
import com.roje.game.core.server.ServerInfo;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;


public class ClusterClientChannelInitializer extends DefaultChannelInitializer {
    private final ClusterClientConfig clientConfig;

    private final ISessionManager sessionManager;

    private final MessageDispatcher dispatcher;

    private final ServerInfo serverInfo;

    public ClusterClientChannelInitializer(ClusterClientConfig clientConfig,
                                           MessageDispatcher dispatcher,
                                           ISessionManager sessionManager,
                                           ServerInfo serverInfo) {
        this.clientConfig = clientConfig;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
        this.serverInfo = serverInfo;
    }

    @Override
    public void custom(ChannelPipeline pipeline) throws Exception {
        pipeline.addLast(new IdleStateHandler(clientConfig.getReaderIdleTime(),clientConfig.getWriterIdleTime(),clientConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultInnerTcpClientChannelInBoundHandler(dispatcher, sessionManager,serverInfo));
    }
}
