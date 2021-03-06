package com.roje.game.hall.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.hall.entity.HRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class HallSessionManager extends SessionManager<HRole> {

    public HallSessionManager(UserRedisService userRedisService,
                              AuthLock authLock,
                              ServerInfo serverInfo,
                              Service service) {
        super(userRedisService,  authLock, serverInfo, service);
    }

    @Override
    protected void kickRole(HRole role) {

    }

    @Override
    public HRole createRole(String account) {
        return null;
    }
}
