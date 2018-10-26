package com.roje.game.core.entity;

import com.roje.game.core.thread.RoomScheduledExecutorService;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter@Setter
public class Room implements Serializable {
    private static final long serialVersionUID = -1871955572775979409L;

    private transient RoomScheduledExecutorService executor;

    private int id;
}
