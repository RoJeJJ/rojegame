package com.roje.game.core.manager.room;

import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.RoomScheduledExecutorService;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class RoomManager<R extends Role, K extends Room> implements RoomHelper<R, K> {

    private final RoomProperties properties;

    private final RoomRedisService roomRedisService;

    private final ServerInfo serverInfo;

    private final Service service;

    private final Map<Long, K> roomIds = new ConcurrentHashMap<>();

    private final Map<String, List<K>> roleRooms = new ConcurrentHashMap<>();

    private final Map<String, K> joinedRoomMap = new ConcurrentHashMap<>();

    private final Map<K,RoomScheduledExecutorService> executorMap = new ConcurrentHashMap<>();

    private List<Integer> idPool = Collections.synchronizedList(new ArrayList<>());

    public RoomManager(RoomProperties properties,
                       RoomRedisService roomRedisService,
                       ServerInfo serverInfo,
                       Service service) {
        this.properties = properties;
        this.roomRedisService = roomRedisService;
        this.serverInfo = serverInfo;
        this.service = service;
        for (int i = 0; i < 100000; i++)
            idPool.add(i);
    }

    private int getId() {
        int index = new Random().nextInt(idPool.size());
        return idPool.get(index);
    }

    @Override
    public K createRoom(CreateCardRoomRequest request) throws RJException {
        if (roomIds.size() >= properties.getMaxRoomSize()) {
            log.info("服务器房间已经满了");
            throw new RJException(ErrorData.CREATE_ROOM_ROOM_FULL);
        }
        K room = joinedRoomMap.get(request.getAccount());
        if (room != null) {
            log.info("已经在房间中了,不能再创建房间");
            throw new RJException(ErrorData.CREATE_ROOM_CAN_NOT_IN_ROOM);
        }
        List<K> rooms = roleRooms.get(request.getAccount());
        if (rooms.size() >= properties.getUserMaxCreateCount()) {
            log.info("超过用户最大创建房间数:{},已经创建:{}",
                    properties.getUserMaxCreateCount(), rooms.size());
            throw new RJException(ErrorData.CREATE_ROOM_EXCEED_USER_LIMIT);
        }

        // TODO: 2018/11/2 检查房卡是否足够

        long id = serverInfo.getId() * 100000 + getId();
        room = createRoom0(request);
        room.setId(id);
        RoomScheduledExecutorService executorService =
                service.getRoomExecutor().allocateExecutor(room);
        executorMap.put(room,executorService);

        roomIds.put(id, room);
        roleRooms.
                computeIfAbsent(request.getAccount().intern(),
                        s -> new ArrayList<>())
                .add(room);
        roomRedisService.save(room);
        return room;
    }

    public abstract K createRoom0(CreateCardRoomRequest request) throws RJException;

    public void entryRoom(R role, long roomId) {
        K room = roomIds.get(roomId);
        if (room != null){
            executorMap.get(room).getExecutorService().execute(() ->
                    room.enter(role));
        }
    }
}
