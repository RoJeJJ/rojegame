package com.roje.game.niuniu.manager;

import com.roje.game.core.manager.room.RoomHelper;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;
import org.springframework.stereotype.Component;

@Component
public class NNSessionManager extends SessionManager<NNRole, NNRoom> {

    public NNSessionManager(UserRedisService userRedisService,
                            RoomHelper<NNRole,NNRoom> roomManager,
                            AuthLock authLock,
                            ServerInfo serverInfo,
                            Service service) {
        super(userRedisService, roomManager, authLock, serverInfo, service);
    }

    @Override
    protected void kickRole(NNRole role) {

    }

    @Override
    public NNRole createRole(String account) {
        return null;
    }
}
