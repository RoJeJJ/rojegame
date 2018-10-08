package com.roje.game.gate.server;

import com.roje.game.core.manager.UserSessionManager;
import com.roje.game.core.session.Session;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.TcpServerService;

public class GateToUserTcpTcpServer extends TcpServerService {
    private UserSessionManager<Session> userSessionManager;
    public GateToUserTcpTcpServer(ThreadConfig config, NettyTcpServer tcpServer){
        super(config,tcpServer);
    }

    @Override
    public void onShutDown() {
        userSessionManager.onShutDown();
    }

    public void setUserSessionManager(UserSessionManager<Session> userSessionManager) {
        this.userSessionManager = userSessionManager;
    }

    public UserSessionManager<Session> getUserSessionManager() {
        return userSessionManager;
    }
}
