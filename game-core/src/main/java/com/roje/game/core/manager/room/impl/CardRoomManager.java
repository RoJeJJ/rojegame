package com.roje.game.core.manager.room.impl;

import com.google.protobuf.Any;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CardRoomManager<M extends CardRoom> extends RoomManager {
    private final Service service;
    private final RoomRedisService roomRedisService;
    public CardRoomManager(RoomProperties properties,
                           ServerInfo serverInfo,
                           Service service,
                           RoomRedisService roomRedisService) {
        super(properties, serverInfo);
        this.service = service;
        this.roomRedisService = roomRedisService;
    }

    public synchronized <R extends RoomRole> M createRoom(R role, Any any){
        if (roomIds.size() >= getMaxRoomSize()) {
            log.info("服务器房间已经满了");
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.CREATE_ROOM_ROOM_FULL);
            return null;
        } else {
            long id = generateId();
            M room = createRoom0(id,role,getRoomMaxRoles(),any);
            if (room != null) {
                RoomScheduledExecutorService executor = service.getRoomExecutor().allocateExecutor(room);
                room.setExecutor(executor);
                room.setRoomListener(this);
                roomIds.put(room.id(),room);
                roomRedisService.save(room);
            }else
                recoverId(id);
            return room;
        }
    }

    protected abstract <R extends RoomRole> M createRoom0(long id, R role, int roomMaxRole, Any any);
}
