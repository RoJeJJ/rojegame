package com.roje.game.niuniu.data;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NNRole extends DefaultRoomRole {
    public NNRole(long id,
                  String account,
                  String avatar,
                  String nickname,
                  long card,
                  long gold) {
        super(id, account, avatar, nickname, card, gold);
    }
}
