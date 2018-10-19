package com.roje.game.core.service.redis;

import com.roje.game.core.entity.User;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class UserRedisService {

    private static final String USER_REDIS = "user-redis";

    private static final String GAME_TOKEN = "game-token";

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

    public String generateToken(User user){
        String token = UUID.randomUUID().toString().replace("-","");
        userRedisTemplate.opsForHash().put(GAME_TOKEN,token,user);
        return token;
    }

    public String getToken(String account){
        return (String) userRedisTemplate.opsForHash().get(USER_REDIS,account);
    }
}
