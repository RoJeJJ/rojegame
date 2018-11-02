package com.roje.game.core.manager.room;

import com.roje.game.core.entity.Role;
import com.roje.game.core.exception.RJException;
import com.roje.game.message.create_room.CreateCardRoomRequest;

public interface RoomHelper<R extends Role,M> {
    M createRoom(CreateCardRoomRequest request) throws RJException;
}
