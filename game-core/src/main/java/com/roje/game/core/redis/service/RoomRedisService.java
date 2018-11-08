package com.roje.game.core.redis.service;

import com.roje.game.core.entity.room.Room;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Map;

public class RoomRedisService {
    private static final String ROOM_REDIS = "room-redis";

    private final RedisTemplate<Object,Object> redisTemplate;

    public RoomRedisService(RedisTemplate<Object,Object> redisTemplate){
        this.redisTemplate = redisTemplate;
    }

    public <T extends Room> void save(T room){
        redisTemplate.opsForHash().put(ROOM_REDIS,room.id(),room);
    }

    public Map<Object, Object> getRooms(){
        return redisTemplate.opsForHash().entries(ROOM_REDIS);
    }


    @SuppressWarnings("unchecked")
    public <T extends Room> T getRoom(int roomId){
        return (T) redisTemplate.opsForHash().get(ROOM_REDIS,roomId);
    }
}
