package com.roje.game.core.entity;

import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter@Setter
@Slf4j
public abstract class Role<T extends Room> {
    protected int id;

    protected String account;

    protected boolean online;

    protected T joinedRoom;

    protected List<T> createRooms = new ArrayList<>();

    protected Channel channel;
}
