package com.roje.game.golden.manager;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.golden.data.GFCardRole;
import org.springframework.stereotype.Component;

@Component
public class GFSessionManager extends SessionManager {
    public GFSessionManager(UserRedisService userRedisService,
                            AuthLock authLock,
                            ServerInfo serverInfo) {
        super(userRedisService, authLock, serverInfo);
    }

    @Override
    protected <R extends Role> void kickRole(R role) {

    }

    @Override
    public <R extends Role> R createRole(String account) {
        return null;
    }
}
