package com.roje.game.gate.netty.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.session.GateUserSession;
import io.netty.channel.ChannelHandlerContext;

public class GateGameServerChannelInBoundHandler extends DefaultInBoundHandler {
    private GateUserSessionManager sessionManager;
    public GateGameServerChannelInBoundHandler(boolean containUid, Service service, MessageDispatcher dispatcher) {
        super(containUid,service,dispatcher);
    }

    public void setSessionManager(GateUserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void forward(ChannelHandlerContext ctx, int mid, long uid, byte[] bytes) {
        GateUserSession session = sessionManager.getLoggedSession(uid);
        session.send(mid,bytes);
    }
}
