package com.roje.game.core.service.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Random;

public class IdService {

    private static final String USER_ID_COUNTER = "user-id-num";

    private final StringRedisTemplate redisTemplate;

    public IdService(StringRedisTemplate redisTemplate){
        this.redisTemplate = redisTemplate;
        this.redisTemplate.opsForValue().set(USER_ID_COUNTER,"100000");
    }

    public Long genrate(){
        long delta = new Random().nextInt(4)+1;
        return this.redisTemplate.opsForValue().increment(USER_ID_COUNTER,delta);
    }
}
