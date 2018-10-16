package com.roje.game.gate.netty.channel;

import com.roje.game.core.config.NettyTcpServerConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateSessionManager;
import com.roje.game.gate.netty.handler.GateUserServerChannelInBoundHandler;
import com.roje.game.gate.session.GateUserSession;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("gateUserTcpServerChannelInitializer")
public class GateUserTcpServerChannelInitializer extends DefaultChannelInitializer {
    private final NettyTcpServerConfig nettyTcpServerConfig;

    private final MessageDispatcher dispatcher;

    private final SessionManager<GateUserSession> sessionManager;

    private final Service gateUserExecutorService;

    public GateUserTcpServerChannelInitializer(
            @Qualifier("gateUserTcpConfig") NettyTcpServerConfig nettyTcpServerConfig,
            MessageDispatcher dispatcher,
            SessionManager<GateUserSession> sessionManager,
            Service service) {
        this.nettyTcpServerConfig = nettyTcpServerConfig;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
        this.gateUserExecutorService = service;
    }

    @Override
    public void custom(ChannelPipeline pipeline) throws Exception {
        pipeline.addLast(new IdleStateHandler(nettyTcpServerConfig.getReaderIdleTime(), nettyTcpServerConfig.getWriterIdleTime(), nettyTcpServerConfig.getAllIdleTime()));
        pipeline.addLast(new GateUserServerChannelInBoundHandler(gateUserExecutorService, dispatcher, sessionManager));
    }
}
