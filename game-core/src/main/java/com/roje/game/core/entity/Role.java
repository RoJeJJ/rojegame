package com.roje.game.core.entity;


import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Role {
    protected String account;
    protected long id;
    protected Channel channel;
}
