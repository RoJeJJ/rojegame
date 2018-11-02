package com.roje.game.core.redis.message;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class RedisMessage {
    private int fromServerId;
    private int toServerId;
    private byte[] data;
}
