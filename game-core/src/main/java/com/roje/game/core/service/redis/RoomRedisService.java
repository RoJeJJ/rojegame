package com.roje.game.core.service.redis;

import com.roje.game.core.entity.Room;
import com.roje.game.core.server.ServerInfo;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Random;

public class RoomRedisService {
    private static final String ROOM_REDIS = "room-redis";

    private final RedisTemplate<Object,Object> redisTemplate;

    public RoomRedisService(RedisTemplate<Object,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public synchronized <T extends Room> int generate(ServerInfo serverInfo, T room){
        Random random = new Random();
        int id = random.nextInt(100000)+serverInfo.getGameId()*100000;
        while (redisTemplate.opsForHash().hasKey(ROOM_REDIS,id)){
            id = random.nextInt(100000)+serverInfo.getGameId()*100000;
        }
        redisTemplate.opsForHash().put(ROOM_REDIS,id,room);
        return id;
    }
}
