package com.roje.game.niuniu.manager;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.thread.executor.TaskExecutor;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNRoom;

public class NNSessionManager extends SessionManager<NNRole, NNRoom> {



    public NNSessionManager(UserRedisService userRedisService,
                            ServerInfo info,
                            TaskExecutor<String> userExecutor,
                            NNRoomManager roomManager) {
        super(userRedisService, info, userExecutor, roomManager);
    }

    @Override
    public NNRole createRole() {
        NNRole role = new NNRole();
        return null;
    }
}
