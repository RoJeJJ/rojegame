package com.roje.game.golden.data;

import com.google.protobuf.Message;
import com.roje.game.core.entity.role.impl.RoomRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.exception.RJException;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.GameStart;
import com.roje.game.message.gf.GFCardRoomConfig;

import java.util.Collections;
import java.util.List;

public class GFCardRoom extends CardRoom {

    private List<Integer> pokers;

    private GFCardRole banker;

    public GFCardRoom(long id,
                      GFCardRole role,
                      int maxRoomRoles,
                      GFCardRoomConfig config) {
        super(id, role, config.getPerson(), maxRoomRoles, config.getRound());
    }

    @Override
    protected Message roomInfo(RoomRole role) {
        return null;
    }

    @Override
    protected void enter0(RoomRole role) throws RJException {
    }

    @Override
    protected void initStart0() {
        pokers.clear();
        for (int i = 2; i <= 14; i++) {
            for (int j = 0; j < 4; j++)
                pokers.add(2 * 4 + j);
        }

        //洗牌
        Collections.shuffle(pokers);

        //通知房间中所有人,游戏开始
        GameStart.Builder builder = GameStart.newBuilder();
        sendAll(Action.GameStartNotice, builder.build());
        delayExecute(2000, GFCardRoom::deal);
    }

    @Override
    protected void onSit(RoomRole role) {
        if (curHead() >= 2)
            startGame();
    }

    private void deal() {
        if (banker == null){

        }
        for (GFCardRole role:this.<GFCardRole>sitRoles()){

        }
    }
}
