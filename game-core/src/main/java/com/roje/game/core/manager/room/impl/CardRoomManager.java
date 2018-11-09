package com.roje.game.core.manager.room.impl;

import com.google.protobuf.Any;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
public abstract class CardRoomManager<R extends CardRole,M extends CardRoom> extends RoomManager<R,M> {

    private final Service service;

    private final RoomRedisService roomRedisService;

    private final Map<R, List<M>> createRoomsMap = new ConcurrentHashMap<>();

    public CardRoomManager(RoomProperties properties,
                           ServerInfo serverInfo,
                           Service service,
                           RoomRedisService roomRedisService) {
        super(properties, serverInfo);
        this.service = service;
        this.roomRedisService = roomRedisService;
    }

    public synchronized void createRoom(R role, Any any){
        if (roomIds.size() >= getMaxRoomSize()) {
            log.info("服务器房间已经满了");
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.CREATE_ROOM_ROOM_FULL);
        } else {
            synchronized (role.account().intern()){
                List<M> createRooms = createRoomsMap.get(role);
                if (createRooms != null && createRooms.size() >= getMaxRoomSize())
                    MessageUtil.sendErrorData(role.getChannel(),ErrorData.CREATE_ROOM_ROOM_FULL);
                else {
                    M joined = joinedRoomMap.get(role);
                    if (joined != null) {
                        MessageUtil.sendErrorData(role.getChannel(), ErrorData.CREATE_ROOM_CAN_NOT_IN_ROOM);
                    }else {
                        long id = generateId();
                        M room = createRoom0(id,role,getRoomMaxRoles(),any);
                        if (room != null) {
                            RoomScheduledExecutorService executor = service.getRoomExecutor().allocateExecutor(room);
                            room.setExecutor(executor);
                            room.setRoomListener(this);
                            roomIds.put(room.id(),room);
                            createRoomsMap.computeIfAbsent(role, r -> new ArrayList<>())
                                    .add(room);
                            roomRedisService.save(room);
                        }else
                            recoverId(id);
                    }
                }
            }
        }
    }

    protected abstract M createRoom0(long id, R role, int roomMaxRole, Any any);

    public void joinRoom(R role, long roomId) {
        M room = getRoom(roomId);
        if (room == null){
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.ENTER_ROOM_NO_SUCH_ROOM);
            return;
        }
        synchronized (role.account().intern()){
            M joined = joinedRoomMap.get(role);
            if (joined != null){
                if (joined == room)
                    MessageUtil.sendErrorData(role.getChannel(),ErrorData.ENTER_ROOM_ALREADY_JOIN);
                else
                    MessageUtil.sendErrorData(role.getChannel(),ErrorData.ENTER_ROOM_JOINED_ANOTHER_ROOM);
                return;
            }

            Future<Boolean> future =
                    room.getExecutor().getExecutorService().submit(() -> room.enter(role));
            try {
                boolean success = future.get();
                if (success) {
                    joinedRoomMap.put(role,room);
                }
            }catch (InterruptedException | ExecutionException e) {
                log.warn("id:{}加入房间时,出现异常",role.id());
                MessageUtil.sendErrorData(role.getChannel(),ErrorData.ENTER_ROOM_ERROR);
            }
        }
    }
}
