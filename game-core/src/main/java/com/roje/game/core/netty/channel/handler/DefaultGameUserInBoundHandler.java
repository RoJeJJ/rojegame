package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelHandlerContext;

public class DefaultGameUserInBoundHandler extends DefaultInBoundHandler {

    private SessionManager sessionManager;

    public DefaultGameUserInBoundHandler(Service service, MessageDispatcher dispatcher,SessionManager sessionManager) {
        super(service, dispatcher);
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        sessionManager.sessionOpen(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        sessionManager.sessionClose(ctx.channel());
    }
}
