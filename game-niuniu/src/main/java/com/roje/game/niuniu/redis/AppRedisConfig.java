package com.roje.game.niuniu.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roje.game.core.redis.message.RedisMessageDispatcher;
import com.roje.game.core.redis.sub.RedisReceiver;
import com.roje.game.core.server.ServerInfo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Import({
        StringRedisSerializer.class,
        RedisMessageDispatcher.class,
})
public class AppRedisConfig {

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        return mapper;
    }

    @Bean
    public GenericJackson2JsonRedisSerializer jsonRedisSerializer(
            ObjectMapper objectMapper){
        return new GenericJackson2JsonRedisSerializer(objectMapper);
    }

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory,
                                                      GenericJackson2JsonRedisSerializer jsonRedisSerializer){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        return redisTemplate;
    }

    @Bean
    public RedisReceiver redisReceiver(
            RedisMessageDispatcher dispatcher,
            StringRedisSerializer stringRedisSerializer,
            GenericJackson2JsonRedisSerializer jsonRedisSerializer,
            ServerInfo serverInfo){
        return new RedisReceiver(dispatcher,stringRedisSerializer,jsonRedisSerializer,serverInfo, service);
    }

    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                                   RedisReceiver receiver) {

        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(receiver, new PatternTopic("create-room-req"));

        return container;
    }
}
