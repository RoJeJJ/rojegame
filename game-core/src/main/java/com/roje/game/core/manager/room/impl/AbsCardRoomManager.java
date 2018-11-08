package com.roje.game.core.manager.room.impl;

import com.google.protobuf.Any;
import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.CardRoomRole;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.entity.role.RoomRole;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.core.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
public abstract class AbsCardRoomManager<R extends CardRoomRole<M>,M extends CardRoom<R>> implements CardRoomManager<R,M> {

    private Map<Long,M> roomIds = new ConcurrentHashMap<>();

    private final RoomProperties properties;

    private final RoomRedisService roomRedisService;

    private List<Long> roomIdPool;

    private Map<String,M> joinedMap = new HashMap<>();

    private final Service service;

    public AbsCardRoomManager(RoomProperties properties,
                              RoomRedisService roomRedisService,
                              ServerInfo serverInfo,
                              Service service){
        this.properties = properties;
        this.roomRedisService = roomRedisService;
        this.service = service;
        roomIdPool = new ArrayList<>(100000);
        for (int i=0;i<99999;i++)
            roomIdPool.add((long) (serverInfo.getId()*100000 + i));
    }

    private long generateId(){
        int index = new Random().nextInt(roomIdPool.size());
        return roomIdPool.remove(index);
    }

    private void recoverId(long id){
        roomIdPool.add(id);
    }

    @Override
    public final synchronized void createRoom(R role, Any any) {
        if (roomIds.size() >= getMaxRoomSize()) {
            log.info("服务器房间已经满了");
            MessageUtil.sendErrorData(role.getChannel(),ErrorData.CREATE_ROOM_ROOM_FULL);
        }else {
            long id = generateId();
            M room = createRoom0(id,role,properties.getRoomMaxRole(),any);
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

    @Override
    public void enterRoom(R role, long roomId) {
        M room = getRoom(roomId);
        if (room != null){
            boolean success = enterRoom(role,room);
            if (success)
                joinedMap.put(role.account(),room);
        }else {
            log.info("房间:{}不存在",roomId);
            MessageUtil.sendErrorData(role.getChannel(), ErrorData.ENTER_ROOM_NO_SUCH_ROOM);
        }
    }


    @Override
    public void execute(R role, Consumer<M> consumer){
        M room = joinedMap.get(role.account());
        if (room != null)
            room.getExecutor().getExecutorService().execute(()->consumer.accept(room));
    }

    public abstract M createRoom0(long id, CardRoomRole role, int roomMaxRoles, Any any);

    private boolean enterRoom(R role, M room) {
        return role.enterRoom(room);
    }

    @Override
    public M getRoom(long roomId) {
        return roomIds.get(roomId);
    }

    @Override
    public int getMaxRoomSize() {
        return properties.getMaxRoomSize();
    }

    @Override
    public int getUserMaxCreateRoomCount() {
        return properties.getUserMaxCreateRoomCount();
    }

    @Override
    public synchronized void roomDestroy(long roomId) {
        M room = roomIds.remove(roomId);
        recoverId(roomId);
        if (room != null) {
            for (R role:room.roomRoles()){
                joinedMap.remove(role.account());
            }
        }
    }
}
