package com.roje.game.core.service.redis;

import com.roje.game.core.entity.User;
import org.springframework.data.redis.core.RedisTemplate;

public class UserRedisService {

    private static final String USER_REDIS = "user-redis";

    private final RedisTemplate<Object,Object> userRedisTemplate;

    public UserRedisService(RedisTemplate<Object, Object> userRedisTemplate) {
        this.userRedisTemplate = userRedisTemplate;
    }

    public User get(String account){
        return (User) userRedisTemplate.opsForHash().get(USER_REDIS,account);
    }

    public void save(User user){
        userRedisTemplate.opsForHash().put(USER_REDIS,user.getAccount(),user);
    }
}
