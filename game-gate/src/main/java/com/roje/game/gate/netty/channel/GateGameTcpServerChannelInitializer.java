package com.roje.game.gate.netty.channel;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.netty.handler.GateGameServerChannelInBoundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("gateGameTcpServerChannelInitializer")
public class GateGameTcpServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyServerConfig nettyServerConfig;

    private final MessageDispatcher dispatcher;

    private final GateUserSessionManager sessionManager;

    @Autowired
    public GateGameTcpServerChannelInitializer(@Qualifier("gateGameTcpConfig") NettyServerConfig nettyServerConfig,
                                               MessageDispatcher dispatcher,
                                               GateUserSessionManager sessionManager) {
        this.nettyServerConfig = nettyServerConfig;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(nettyServerConfig.getReaderIdleTime(), nettyServerConfig.getWriterIdleTime(), nettyServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageCodec());
        pipeline.addLast(new GateGameServerChannelInBoundHandler(true,dispatcher,sessionManager));
    }
}
