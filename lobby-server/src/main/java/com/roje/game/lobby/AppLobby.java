package com.roje.game.lobby;

import com.roje.game.core.config.ExecutorProperties;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.thread.executor.TaskExecutor;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Bean("user-executor")
    @ConfigurationProperties("executor.user")
    public ExecutorProperties userExecutorProperties(){
        return new ExecutorProperties();
    }



    @Bean
    public TaskExecutor<String> userExecutor(
            @Qualifier("user-executor") ExecutorProperties properties){
        return new TaskExecutor<>(properties);
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
