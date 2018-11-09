package com.roje.game.core.entity.role.impl;


public abstract class CardRole extends RoomRole {

    public CardRole(long id,
                    String account,
                    String nickname,
                    String avatar,
                    long card,
                    long gold) {
        super(id, account, nickname, avatar, card, gold);
    }
}
