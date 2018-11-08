package com.roje.game.core.entity.role;

import com.google.protobuf.Any;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.manager.room.CardRoomManager;

import java.util.List;

public interface RoomRole extends Role{

    Room getJoinedRoom();

    void setJoinedRoom(Room room);

    List<CardRoom> createRooms();

    int getSeat();

    void setSeat(int seat);

    boolean inGame();

    boolean enterRoom(CardRoom room);

    void createRoom(CardRoomManager manager, Any any);
}
