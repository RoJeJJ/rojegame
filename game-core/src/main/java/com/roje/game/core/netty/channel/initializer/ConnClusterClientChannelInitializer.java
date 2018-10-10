package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.UserManager;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import com.roje.game.core.netty.channel.handler.DefaultToClusterTcpClientChannelInBoundHandler;
import com.roje.game.core.server.BaseInfo;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;


public class ConnClusterClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final NettyConnClusterClientConfig clientConfig;

    private final UserManager userManager;

    private final MessageDispatcher dispatcher;

    private final BaseInfo baseInfo;

    public ConnClusterClientChannelInitializer(NettyConnClusterClientConfig clientConfig,
                                               MessageDispatcher dispatcher,
                                               UserManager userManager,
                                               BaseInfo baseInfo) {
        this.clientConfig = clientConfig;
        this.dispatcher = dispatcher;
        this.userManager = userManager;
        this.baseInfo = baseInfo;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(clientConfig.getReaderIdleTime(),clientConfig.getWriterIdleTime(),clientConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageCodec());
        pipeline.addLast(new DefaultToClusterTcpClientChannelInBoundHandler(dispatcher,userManager,baseInfo));
    }
}
