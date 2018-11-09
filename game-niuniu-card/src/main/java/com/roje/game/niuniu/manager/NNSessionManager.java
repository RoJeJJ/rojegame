package com.roje.game.niuniu.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.niuniu.data.NNRole;
import org.springframework.stereotype.Component;

@Component
public class NNSessionManager extends SessionManager<NNRole> {

    public NNSessionManager(UserRedisService userRedisService,
                            AuthLock authLock,
                            ServerInfo serverInfo) {
        super(userRedisService, authLock, serverInfo, idService);
    }

    @Override
    protected void kickRole(NNRole role) {

    }

    @Override
    public NNRole createRole(String account) {
        return null;
    }
}
