package com.roje.game.login.controller;


import com.roje.game.login.redis.RedisReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/room")
public class RoomController {

    private final RedisReceiver redisReceiver;

    private final RedisTemplate<Object,Object> redisTemplate;

    @Autowired
    public RoomController(RedisReceiver redisReceiver,
                          RedisTemplate<Object,Object> redisTemplate) {
        this.redisReceiver = redisReceiver;
        this.redisTemplate = redisTemplate;
    }

    @PostMapping(value = "/create",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<String> createRoom(@RequestParam("config") String config){
        DeferredResult<String> deferredResult = new DeferredResult<>();
        String uuid = UUID.randomUUID().toString().replace("-","");
        redisReceiver.setDeferredResult(uuid,deferredResult);
        log.info("发布消息");
        redisTemplate.convertAndSend("create-room-req",config);
        return deferredResult;
    }
}
