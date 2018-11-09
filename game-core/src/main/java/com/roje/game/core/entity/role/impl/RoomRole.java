package com.roje.game.core.entity.role.impl;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.entity.room.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Consumer;


@Getter@Setter
public abstract class RoomRole extends Role {

    protected Room joinedRoom;

    protected int seat;

    protected boolean inGame;

    public RoomRole(long id,
                    String account,
                    String nickname,
                    String avatar,
                    long card,
                    long gold) {
        super(id, account, nickname, avatar, card, gold);
    }

    public void roomExecute(Consumer<Room> consumer){
        if (joinedRoom != null)
            joinedRoom.getExecutor().getExecutorService()
                    .execute(()->consumer.accept(joinedRoom));
    }

    public final void initStart(){
        inGame = true;
        initStart0();
    }

    /**
     * 此方法在开始游戏后调用,用于初始化玩家的自定义数据
     */
    protected abstract void initStart0();
}
