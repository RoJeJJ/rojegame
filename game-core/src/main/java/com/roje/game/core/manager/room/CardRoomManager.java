package com.roje.game.core.manager.room;

import com.google.protobuf.Any;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.entity.role.RoomRole;

public interface CardRoomManager<R extends RoomRole,M extends CardRoom<R>> extends RoomManager<R,M> {

    void createRoom(R role, Any any);

    void enterRoom(R role,long roomId);

    M create(R role, Any any);
}
