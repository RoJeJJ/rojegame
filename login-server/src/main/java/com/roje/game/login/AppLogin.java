package com.roje.game.login;

import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.redis.lock.LoginLock;
import com.roje.game.core.service.Service;
import com.roje.game.core.redis.service.IdService;
import com.roje.game.core.redis.service.UserRedisService;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.IOException;

@SpringBootApplication
public class AppLogin {
    public static void main(String[] args) {
        SpringApplication.run(AppLogin.class);
    }

    @Bean
    public ThreadProperties threadProperties() {
        return new ThreadProperties();
    }

    @Bean
    public Service service(ThreadProperties properties) {
        return new Service(properties);
    }


    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object, Object> redisTemplate) {
        return new UserRedisService(redisTemplate);
    }

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
    }

    @Bean
    public LoginLock AccountLock(RedissonClient redissonClient){
        return new LoginLock(redissonClient);
    }

    @Bean
    public IdService idService(StringRedisTemplate stringRedisTemplate) {
        return new IdService(stringRedisTemplate);
    }
}
