package com.roje.game.core.redis.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

public class LoginLock {
    private final RedissonClient redissonClient;
    public LoginLock(RedissonClient client){
        this.redissonClient = client;
    }

    public RLock getLock(String account){
        return redissonClient.getLock("lock-login-"+account);
    }
}
