package com.roje.game.core.manager.room.impl;

import com.google.protobuf.Any;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.CardRoomRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CardRoomManager extends RoomManager {
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

    public synchronized CardRoom createRoom(CardRoomRole role, Any any){
        if (roomIds.size() >= properties.getMaxRoomSize()) {
            log.info("服务器房间已经满了");
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.CREATE_ROOM_ROOM_FULL);
            return null;
        } else {
            long id = generateId();
            CardRoom room = createRoom0(id,role,properties.getRoomMaxRole(),any);
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

    protected abstract CardRoom createRoom0(long id, CardRoomRole role, int roomMaxRole, Any any);
}
