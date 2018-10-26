package com.roje.game.core.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.io.Serializable;

public class RedisMessageSender {
  private RedisTemplate<Object, Object> redisTemplate;
  public RedisMessageSender(RedisTemplate<Object,Object> redisTemplate){
      this.redisTemplate = redisTemplate;
  }
  public void sendMessage(String channel, Serializable message) {
    redisTemplate.convertAndSend(channel, message);
  }
}