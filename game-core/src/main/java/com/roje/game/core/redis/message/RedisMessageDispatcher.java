package com.roje.game.core.redis.message;

import java.util.HashMap;
import java.util.Map;

public class RedisMessageDispatcher {
    /**
     * key,channel
     */
    private Map<String,RedisMessageHandler> handlers = new HashMap<>();

    public void register(RedisMessageHandler handler){
        RedisMsgProcessor processor = handler.getClass().getAnnotation(RedisMsgProcessor.class);
        if (processor != null)
            handlers.put(processor.channel(),handler);
    }

    public RedisMessageHandler getHandler(String channel){
        return handlers.get(channel);
    }
}
