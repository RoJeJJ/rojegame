package com.roje.game.core.redis.service;

import com.roje.game.core.entity.User;
import com.roje.game.core.server.ServerInfo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;

public class UserRedisService {

    private static final String USER_REDIS = "user-redis";

    private static final String GAME_TOKEN = "game-token";

    private static final String USER_CONNECTION_SERVER = "user_connection_server";

    private static final String USER_ALLOCATION_SERVER_SUFFIX = "_allocation_server";

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

    public boolean contain(String account){
        return userRedisTemplate.opsForHash().hasKey(USER_REDIS,account);
    }

    public String generateToken(String account){
        String token = UUID.randomUUID().toString().replace("-","");
        userRedisTemplate.opsForHash().put(GAME_TOKEN,account,token);
        return token;
    }

    public String getToken(String account){
        return (String) userRedisTemplate.opsForHash().get(GAME_TOKEN,account);
    }

    public void bindLoggedServer(String account, ServerInfo info){
        userRedisTemplate.opsForHash().put(USER_CONNECTION_SERVER,account,info);
    }

    public ServerInfo getLoggedServer(String account){
        return (ServerInfo) userRedisTemplate.opsForHash().get(USER_CONNECTION_SERVER,account);
    }

    public void allocateServer(String account, ServerInfo info) {
        userRedisTemplate.opsForHash().put(account+USER_ALLOCATION_SERVER_SUFFIX, info.getType(), info);
    }

    public ServerInfo getAllocateServer(String account,int gameId) {
        return (ServerInfo) userRedisTemplate.opsForHash().get(account+USER_ALLOCATION_SERVER_SUFFIX, gameId);
    }
}
