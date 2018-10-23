package com.roje.game.lobby;

import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class AppLobby {
    public static void main(String[] args) {
        SpringApplication.run(AppLobby.class);
    }

    @Bean
    public ThreadProperties threadProperties(){
        return new ThreadProperties();
    }

    @Bean
    public Service service(ThreadProperties properties){
        return new Service(properties);
    }

    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new UserRedisService(redisTemplate);
    }

    @Bean
    public IdService idService(StringRedisTemplate stringRedisTemplate){
        return new IdService(stringRedisTemplate);
    }
}
