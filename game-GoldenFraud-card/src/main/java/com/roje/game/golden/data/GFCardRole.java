package com.roje.game.golden.data;


import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.room.impl.CardRoomManager;

public class GFCardRole extends CardRole {
    public GFCardRole(long id,
                      String account,
                      String nickname,
                      String avatar,
                      long card,
                      long gold,
                      CardRoomManager roomManager) {
        super(id, account, nickname, avatar, card, gold, roomManager);
    }
}
