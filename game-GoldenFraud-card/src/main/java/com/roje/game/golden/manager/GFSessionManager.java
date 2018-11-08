package com.roje.game.golden.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.golden.data.GFRole;
import org.springframework.stereotype.Component;

@Component
public class GFSessionManager extends SessionManager<GFRole> {

    public GFSessionManager(UserRedisService userRedisService,
                            AuthLock authLock,
                            ServerInfo serverInfo) {
        super(userRedisService, authLock, serverInfo);
    }
    @Override
    protected void kickRole(GFRole role) {

    }
    @Override
    public GFRole createRole(String account) {
        return null;
    }
}
