package com.roje.game.niuniu.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;

public class NNSessionManager extends SessionManager<NNRole, NNRoom> {



    public NNSessionManager(UserRedisService userRedisService,
                            ServerInfo info,
                            Service service,
                            NNRoomManager roomManager) {
        super(userRedisService, info, service, roomManager);
    }

    @Override
    public NNRole createRole() {
        // TODO: 2018/10/23 创建新role
        return null;
    }
}
