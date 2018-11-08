package com.roje.game.core.entity.role.impl;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.entity.room.Room;
import lombok.Getter;
import lombok.Setter;


@Getter@Setter
public class RoomRole extends Role {

    protected Room joinedRoom;

    private int seat;

    public RoomRole(long id,
                    String account,
                    String nickname,
                    String avatar,
                    long card,
                    long gold) {
        super(id, account, nickname, avatar, card, gold);
    }
}
