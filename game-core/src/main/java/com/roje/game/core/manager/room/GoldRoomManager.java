package com.roje.game.core.manager.room;

import com.roje.game.core.entity.role.RoomRole;
import com.roje.game.core.entity.room.GoldRoom;

public interface GoldRoomManager<R extends RoomRole,M extends GoldRoom<R>> extends RoomManager<R,M> {
}
