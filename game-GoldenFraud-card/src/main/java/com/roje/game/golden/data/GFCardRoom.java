package com.roje.game.golden.data;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.RJException;
import com.roje.game.message.nn.GFCardRoomConfig;

public class GFCardRoom extends CardRoom {

    public <R extends RoomRole> GFCardRoom(long id, R role, int maxRoomRoles,GFCardRoomConfig config) {
        super(id, role, config.getPerson(), maxRoomRoles);
    }

    @Override
    protected <R extends RoomRole> Message roomInfo(R role) {
        return null;
    }

    @Override
    protected <R extends RoomRole> void enter0(R role) throws RJException {

    }

    @Override
    protected <R extends RoomRole> void onSit(R role) {

    }
}
