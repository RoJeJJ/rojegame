package com.roje.game.core.manager.room;

import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.entity.room.Room;
import com.roje.game.core.server.ServerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public abstract class RoomManager<R extends RoomRole,M extends Room> implements Room.RoomListener<M> {

    protected final Map<Long, M> roomIds = new ConcurrentHashMap<>();

    protected final Map<R,M> joinedRoomMap = new ConcurrentHashMap<>();

    private final RoomProperties properties;

    private final List<Long> roomIdPool;

    public RoomManager(RoomProperties properties,
                       ServerInfo serverInfo) {
        this.properties = properties;
        roomIdPool = new ArrayList<>(100000);
        for (int i=0;i<99999;i++)
            roomIdPool.add((long) (serverInfo.getId()*100000 + i));
    }

    protected long generateId(){
        int index = new Random().nextInt(roomIdPool.size());
        return roomIdPool.remove(index);
    }

    protected void recoverId(long id){
        roomIdPool.add(id);
    }

    protected M getRoom(long roomId) {
        return roomIds.get(roomId);
    }

    protected int getMaxRoomSize() {
        return properties.getMaxRoomSize();
    }

    protected int getRoomMaxRoles(){
        return properties.getRoomMaxRole();
    }

    protected int getUserMaxCreateRoomCount() {
        return properties.getUserMaxCreateRoomCount();
    }

    @Override
    public void roomDestroy(M room) {
        roomIds.remove(room.id());
    }

    public M getJoinedRoom(R role){
        return joinedRoomMap.get(role);
    }

    public void roomExecute(R role,Consumer<M> consumer){
        M room = getJoinedRoom(role);
        if (room != null)
            room.getExecutor().getExecutorService().execute(() -> consumer.accept(room));
    }
}
