package com.roje.game.core.thread.executor;

import com.roje.game.core.entity.room.Room;
import com.roje.game.core.thread.RoomScheduledExecutorService;

import java.util.HashMap;
import java.util.Map;

public class RoomExecutor {
    private Map<Room, RoomScheduledExecutorService> executors = new HashMap<>();

    private int maxRoomSize;

    public RoomExecutor(int maxRoomSize){
        this.maxRoomSize = maxRoomSize;
    }

    public synchronized RoomScheduledExecutorService allocateExecutor(Room room){
        RoomScheduledExecutorService executorService = executors.get(room);
        if (executorService != null)
            return executorService;
        executorService = executors.values().stream()
                .filter(executor -> executor.getRooms().size() < maxRoomSize)
                .findAny()
                .orElse(new RoomScheduledExecutorService());
        executorService.addRoom(room);
        executors.put(room,executorService);
        return executorService;
    }

    public synchronized void remove(Room room){
        RoomScheduledExecutorService executorService = executors.remove(room);
        if (executorService != null)
            executorService.remove(room);
    }
}
