package com.roje.game.core.manager.session;

import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.message.create_room.EntryRoomRequest;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

@Component
public abstract class GameSessionManager<R extends Role,M extends Room> extends SessionManager<R, M> {

    private RoomManager<R,M> roomManager;

    public GameSessionManager(UserRedisService userRedisService,
                              RoomManager<R,M> roomManager,
                              AuthLock authLock,
                              ServerInfo serverInfo,
                              Service service) {
        super(userRedisService, roomManager, authLock, serverInfo, service);
        this.roomManager = roomManager;
    }

    public void entryRoom(Channel channel, EntryRoomRequest request){
        R role = getRole(channel);
        if (role == null)
            return;
        roomManager.entryRoom(role,request.getRoomId());
    }
}
