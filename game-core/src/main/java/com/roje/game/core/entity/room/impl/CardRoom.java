package com.roje.game.core.entity.room.impl;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;

public class CardRoom extends Room {

    public CardRoom(long id, int person) {
        super(id, person);
    }

    @Override
    public <R extends RoomRole> Message roomInfo(R role) {
        return null;
    }

    public <R extends RoomRole> boolean enter(R role) {
    }
}
