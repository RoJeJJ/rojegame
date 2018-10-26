package com.roje.game.niuniu.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;
import org.redisson.api.RedissonClient;

public class NNSessionManager extends SessionManager<NNRole, NNRoom> {



    public NNSessionManager(UserRedisService userRedisService,
                            NNRoomManager roomManager,
                            AuthLock authLock) {
        super(userRedisService, roomManager, authLock);
    }

    @Override
    public NNRole createRole() {
        // TODO: 2018/10/23 创建新role
        return null;
    }
}
