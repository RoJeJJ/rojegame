package com.roje.game.core.redis.sub;

import com.roje.game.core.redis.message.RedisMessage;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.message.RedisMessageHandler;
import com.roje.game.core.redis.message.RedisMsgProcessor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.Executor;


public class RedisReceiver implements MessageListener {

    private final RedisMessageDispatcher dispatcher;

    private final StringRedisSerializer stringRedisSerializer;

    private final GenericJackson2JsonRedisSerializer jsonRedisSerializer;

    private final ServerInfo serverInfo;

    private final Service service;

    public RedisReceiver(RedisMessageDispatcher dispatcher,
                         StringRedisSerializer stringRedisSerializer,
                         GenericJackson2JsonRedisSerializer jsonRedisSerializer,
                         ServerInfo serverInfo,
                         Service service) {
        this.dispatcher = dispatcher;
        this.stringRedisSerializer = stringRedisSerializer;
        this.jsonRedisSerializer = jsonRedisSerializer;
        this.serverInfo = serverInfo;
        this.service = service;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channel = stringRedisSerializer.deserialize(message.getChannel());
        RedisMessage redisMessage = jsonRedisSerializer.deserialize(message.getBody(), RedisMessage.class);
        if (redisMessage == null)
            return;
        if (redisMessage.getToServerId() != 0 && redisMessage.getToServerId() != serverInfo.getId())
            return;
        RedisMessageHandler handler = dispatcher.getHandler(channel);
        if (handler != null) {
            handler.setSenderId(redisMessage.getFromServerId());
            RedisMsgProcessor processor = handler.getClass().getAnnotation(RedisMsgProcessor.class);
            Executor executor = service.getExecutor(processor.thread());
            if (executor != null)
                executor.execute(() -> handler.handle(redisMessage.getData()));
            else
                handler.handle(redisMessage.getData());
        }
    }
}
