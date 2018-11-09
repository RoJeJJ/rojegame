package com.roje.game.golden.manager;

import com.roje.game.core.entity.User;
import com.roje.game.core.entity.role.Role;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.golden.data.GFCardRole;
import org.springframework.stereotype.Component;

@Component
public class GFSessionManager extends SessionManager<GFCardRole> {

    private final GFCardRoomManager roomManager;

    public GFSessionManager(UserRedisService userRedisService,
                            AuthLock authLock,
                            ServerInfo serverInfo,
                            GFCardRoomManager roomManager) {
        super(userRedisService, authLock, serverInfo);
        this.roomManager = roomManager;
    }

    @Override
    protected void kickRole(GFCardRole role) {

    }

    @Override
    public GFCardRole createRole(User user) {
       return new GFCardRole(user.getId(),user.getAccount(),user.getNickname(),user.getHeadimg(),
                        user.getCard(),user.getGold(),roomManager);
    }
}
