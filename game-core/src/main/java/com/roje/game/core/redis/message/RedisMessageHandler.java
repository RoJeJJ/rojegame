package com.roje.game.core.redis.message;

import lombok.Getter;
import lombok.Setter;

public abstract class RedisMessageHandler {

    @Getter@Setter
    private int senderId;

    public RedisMessageHandler(RedisMessageDispatcher dispatcher){
        dispatcher.register(this);
    }

    public abstract void handle(byte[] data);
}
