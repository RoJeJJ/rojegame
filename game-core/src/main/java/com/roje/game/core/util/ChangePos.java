package com.roje.game.core.util;

import com.roje.game.core.entity.role.RoomRole;
import lombok.Getter;

@Getter
public class ChangePos<R extends RoomRole> {

    private R exchangee;

    private R applicant;

    private long token;

    public ChangePos(R applicant,R exchangee,long token){
        this.applicant = applicant;
        this.exchangee = exchangee;
        this.token = token;
    }
}
