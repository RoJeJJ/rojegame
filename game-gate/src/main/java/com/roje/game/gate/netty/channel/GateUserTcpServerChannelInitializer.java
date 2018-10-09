package com.roje.game.gate.netty.channel;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.netty.handler.GateUserServerChannelInBoundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.stereotype.Component;

@Component("gateUserTcpServerChannelInitializer")
public class GateUserTcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private final NettyServerConfig nettyServerConfig;

    private final MessageDispatcher dispatcher;

    private final GateUserSessionManager sessionManager;

    private final Service gateUserExecutorService;

    public GateUserTcpServerChannelInitializer(NettyServerConfig nettyServerConfig,
                                               MessageDispatcher dispatcher,
                                               GateUserSessionManager sessionManager,
                                               Service service) {
        this.nettyServerConfig = nettyServerConfig;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
        this.gateUserExecutorService = service;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(nettyServerConfig.getReaderIdleTime(), nettyServerConfig.getWriterIdleTime(), nettyServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageCodec());
        pipeline.addLast(new GateUserServerChannelInBoundHandler(false,gateUserExecutorService,dispatcher,sessionManager));
    }
}
