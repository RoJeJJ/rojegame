package com.roje.game.core.service.redis;

import com.roje.game.core.entity.User;
import com.roje.game.core.server.ServerInfo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class UserRedisService {

    private static final String USER_REDIS = "user-redis";

    private static final String GAME_TOKEN = "game-token";

    private static final String USER_SERVER_IP = "user_server_ip";

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

    public void bindServer(String account, ServerInfo info){
        userRedisTemplate.opsForHash().put(USER_SERVER_IP,account,info);
    }

    public ServerInfo getServer(String account){
        return (ServerInfo) userRedisTemplate.opsForHash().get(USER_SERVER_IP,account);
    }

}
