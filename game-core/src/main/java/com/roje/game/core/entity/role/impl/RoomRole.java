package com.roje.game.core.entity.role.impl;

import com.roje.game.core.entity.role.Role;
import lombok.Getter;
import lombok.Setter;



@Getter@Setter
public abstract class RoomRole extends Role {

    public RoomRole(long id,
                    String account,
                    String nickname,
                    String avatar,
                    long card,
                    long gold) {
        super(id, account, nickname, avatar, card, gold);
    }

    public final void initStart(){
        initStart0();
    }

    /**
     * 此方法在开始游戏后调用,用于初始化玩家的自定义数据
     */
    protected abstract void initStart0();
}
