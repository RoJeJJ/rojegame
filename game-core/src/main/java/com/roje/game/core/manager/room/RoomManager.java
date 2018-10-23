package com.roje.game.core.manager.room;

import com.roje.game.core.config.GameProperties;
import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.redis.RoomRedisService;
import com.roje.game.message.frame.Frame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class RoomManager<M extends Room,R extends Role<M>> {

    private final GameProperties properties;

    private final RoomRedisService roomRedisService;

    private final ServerInfo serverInfo;

    public RoomManager(GameProperties properties, RoomRedisService roomRedisService, ServerInfo serverInfo){
        this.properties = properties;
        this.roomRedisService = roomRedisService;
        this.serverInfo = serverInfo;
    }

    public final M createRoom(R role, Frame frame) throws RJException {
        if (role.getJoinedRoom() != null){
            log.info("已经在房间中了,不能再创建房间");
            throw new RJException(ErrorData.CREATE_ROOM_CAN_NOT_IN_ROOM);
        }
        if (role.getCreateRooms().size() >= properties.getMaxCreateCount()){
            log.info("超过最大创建房间数:{}",properties.getMaxCreateCount());
            throw new RJException(ErrorData.CREATE_ROOM_EXCEED_USER_LIMIT);
        }

        M room =  create(role,frame);
        int id = roomRedisService.generate(serverInfo,room);
        room.setId(id);
        return room;
    }

    protected abstract M create(R role, Frame frame) throws RJException;

}
