package com.roje.game.gate.server;

import com.roje.game.core.manager.SessionSessionManager;
import com.roje.game.core.session.Session;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.TcpServerService;

public class GateToUserTcpTcpServer extends TcpServerService {
    private SessionSessionManager<Session> userSessionManager;
    public GateToUserTcpTcpServer(ThreadConfig config, NettyTcpServer tcpServer){
        super(config,tcpServer);
    }

    @Override
    public void onShutDown() {
        userSessionManager.onShutDown();
    }

    public void setUserSessionManager(SessionSessionManager<Session> userSessionManager) {
        this.userSessionManager = userSessionManager;
    }

    public SessionSessionManager<Session> getUserSessionManager() {
        return userSessionManager;
    }
}
