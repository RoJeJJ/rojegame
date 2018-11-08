package com.roje.game.core.thread;

import com.roje.game.core.entity.room.impl.Room;
import com.roje.game.core.thread.factory.NamedThreadFactory;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Getter@Setter
public class RoomScheduledExecutorService {

    private ScheduledExecutorService executorService;

    private List<Room> rooms;

    public RoomScheduledExecutorService(){
        executorService = Executors.newSingleThreadScheduledExecutor(new NamedThreadFactory("room"));
        rooms = new ArrayList<>();
    }

    public void addRoom(Room room){
        rooms.add(room);
    }

    public void remove(Room room) {
        rooms.remove(room);
    }
}
