package com.roje.game.core.entity.room.impl;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.thread.RoomScheduledExecutorService;


public abstract class Room {

    public interface RoomListener {
        void roomDestroy(Room room);
    }

    private final long id;

    private RoomRole[] sitRoles;

    private boolean gameStart;

    private boolean lock;

    private RoomListener roomListener;

    private RoomScheduledExecutorService executor;


    public Room(long id, int person) {
        this.id = id;
        sitRoles = new RoomRole[person];
        gameStart = false;
        lock = false;
    }

    public long id() {
        return id;
    }

    @SuppressWarnings("unchecked")
    public <R extends RoomRole> R[] sitRoles() {
        return (R[]) sitRoles;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public boolean isLock() {
        return lock;
    }

    public abstract <R extends RoomRole> Message roomInfo(R role);

    public void setRoomListener(RoomListener roomListener) {
        this.roomListener = roomListener;
    }

    public RoomScheduledExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(RoomScheduledExecutorService executor) {
        this.executor = executor;
    }
}
