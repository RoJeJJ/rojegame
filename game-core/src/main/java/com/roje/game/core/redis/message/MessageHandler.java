package com.roje.game.core.redis.message;

import io.netty.handler.codec.redis.RedisMessage;

public interface MessageHandler {
    void handlerMessage();
}
