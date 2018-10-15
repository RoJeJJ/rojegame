package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpClientChannelInBoundHandler;
import com.roje.game.core.server.BaseInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


public class ConnClusterClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final NettyConnClusterClientConfig clientConfig;

    private final SessionManager sessionManager;

    private final MessageDispatcher dispatcher;

    private final BaseInfo baseInfo;

    public ConnClusterClientChannelInitializer(NettyConnClusterClientConfig clientConfig,
                                               MessageDispatcher dispatcher,
                                               SessionManager sessionManager,
                                               BaseInfo baseInfo) {
        this.clientConfig = clientConfig;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
        this.baseInfo = baseInfo;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(clientConfig.getReaderIdleTime(),clientConfig.getWriterIdleTime(),clientConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageDecoder());
        pipeline.addLast(new DefaultInnerTcpClientChannelInBoundHandler(false,null,dispatcher, sessionManager,baseInfo));
    }
}
