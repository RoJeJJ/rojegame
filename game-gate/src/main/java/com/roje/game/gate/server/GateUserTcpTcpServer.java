package com.roje.game.gate.server;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.TcpServerService;
import com.roje.game.core.thread.ServerThread;
import com.roje.game.gate.mannager.UserSessionManager;

import java.util.concurrent.ThreadPoolExecutor;

public class GateUserTcpTcpServer extends TcpServerService {
    private UserSessionManager sessionManager;
    public GateUserTcpTcpServer(ThreadConfig config, NettyTcpServer tcpServer){
        super(config,tcpServer);
    }

    @Override
    public void onShutDown() {
        sessionManager.onShutDown();
    }

    public void setSessionManager(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public UserSessionManager getSessionManager() {
        return sessionManager;
    }
}
