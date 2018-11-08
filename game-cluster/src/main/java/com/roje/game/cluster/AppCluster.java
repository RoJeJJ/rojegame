package com.roje.game.cluster;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.Service;
import com.roje.game.core.redis.service.UserRedisService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@SpringBootApplication
public class AppCluster {
    public static void main(String[] args) {
        SpringApplication.run(AppCluster.class);
    }

    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new UserRedisService(redisTemplate);
    }

    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer(mapper));
        return redisTemplate;
    }

    @Bean
    public Service service(ThreadProperties properties){
        return new Service(properties);
    }

    @Bean
    public ServerSessionManager serverManager(Service service,
                                              UserRedisService userRedisService){
        return new ServerSessionManager(service, userRedisService);
    }

    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadProperties threadConfig() {
        return new ThreadProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "cluster-server.config")
    public NettyServerConfig nettyServerConfig() {
        return new NettyServerConfig();
    }

    @Bean
    public NettyTcpServer clusterTcpServer(
            @Qualifier("clusterTcpChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
            NettyServerConfig nettyServerConfig) {
        return new NettyTcpServer(channelInitializer, nettyServerConfig);
    }

    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }
}
