package com.roje.game.gate.manager;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.manager.SessionSessionManager;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerType;
import com.roje.game.gate.session.GateUserSession;
import org.springframework.beans.factory.annotation.Autowired;


public class GateSessionSessionManager extends SessionSessionManager<GateUserSession> {
    @Autowired
    private ServerManager serverManager;

    public void allocateServer(GateUserSession session) {
        ServerInfo hallServer = serverManager.getIdleServer(ServerType.Hall,session.getVersionCode());
        if (hallServer != null){
            session.setHallServer(hallServer);
        }
        ServerInfo gameServer = serverManager.getIdleServer(ServerType.Game,session.getVersionCode());
        if (gameServer != null){
           session.setGameServer(gameServer);
        }
    }
}
