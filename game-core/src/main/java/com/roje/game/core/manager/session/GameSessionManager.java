package com.roje.game.core.manager.session;

import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;

public abstract class GameSessionManager<R extends Role,M extends Room<R>> extends SessionManager<R, M> {


    public GameSessionManager(UserRedisService userRedisService,
                              AuthLock authLock,
                              ServerInfo serverInfo,
                              Service service) {
        super(userRedisService, authLock, serverInfo, service);
    }
}
