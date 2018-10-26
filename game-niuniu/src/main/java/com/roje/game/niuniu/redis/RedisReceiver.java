package com.roje.game.niuniu.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RedisReceiver {
    public void receiveMessage(String message) {
        log.info("Received <"+ message + ">");
    }
}
