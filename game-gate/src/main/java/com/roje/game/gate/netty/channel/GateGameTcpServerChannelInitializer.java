package com.roje.game.gate.netty.channel;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpServerChannelInBoundHandler;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("gateGameTcpServerChannelInitializer")
public class GateGameTcpServerChannelInitializer extends DefaultChannelInitializer {

    private final NettyTcpServerConfig nettyTcpServerConfig;

    private final MessageDispatcher dispatcher;

    private final ServerManager serverManager;

    @Autowired
    public GateGameTcpServerChannelInitializer(@Qualifier("gateGameTcpConfig") NettyTcpServerConfig nettyTcpServerConfig,
                                               MessageDispatcher dispatcher,
                                               ServerManager serverManager) {
        this.nettyTcpServerConfig = nettyTcpServerConfig;
        this.dispatcher = dispatcher;
        this.serverManager = serverManager;
    }

    @Override
    public void custom(ChannelPipeline pipeline) throws Exception {
        pipeline.addLast(new IdleStateHandler(nettyTcpServerConfig.getReaderIdleTime(), nettyTcpServerConfig.getWriterIdleTime(), nettyTcpServerConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultInnerTcpServerChannelInBoundHandler(null,dispatcher,serverManager));
    }
}
