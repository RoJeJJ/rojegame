package com.roje.game.golden.data;

import com.google.protobuf.Message;
import com.roje.game.core.entity.room.impl.AbsCardRoom;
import com.roje.game.message.nn.GFCardRoomConfig;

public class GFCardRoom extends AbsCardRoom<GFRole> {

    public GFCardRoom(long id,
                      GFRole creator,
                      int roomMaxRoles,
                      Class<GFRole> roleType, GFCardRoomConfig config) {
        super(id, creator, config.getRound(), config.getPayment(), config.getPerson(), roomMaxRoles, roleType);
    }

    @Override
    protected void onSit(GFRole role, int seat) {

    }

    @Override
    public Message createResponse() {
        return null;
    }

    @Override
    public Message roomInfo(GFRole role) {
        return null;
    }
}
