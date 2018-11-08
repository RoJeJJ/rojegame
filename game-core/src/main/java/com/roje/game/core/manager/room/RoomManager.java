package com.roje.game.core.manager.room;

import com.roje.game.core.config.RoomProperties;
import com.roje.game.core.entity.room.Room;
import com.roje.game.core.server.ServerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public abstract class RoomManager implements Room.RoomListener {

    protected final Map<Long, Room> roomIds = new ConcurrentHashMap<>();

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

    @SuppressWarnings("unchecked")
    public <M extends Room> M getRoom(long roomId) {
        return (M) roomIds.get(roomId);
    }

    public int getMaxRoomSize() {
        return properties.getMaxRoomSize();
    }

    public int getRoomMaxRoles(){
        return properties.getRoomMaxRole();
    }

    public int getUserMaxCreateRoomCount() {
        return properties.getUserMaxCreateRoomCount();
    }

    @Override
    public void roomDestroy(Room room) {
        roomIds.remove(room.id());
    }
}
