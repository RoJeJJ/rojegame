package com.roje.game.cluster;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.UserRedisService;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;

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
    public Service service(ThreadProperties properties){
        return new Service(properties);
    }

    @Bean
    public ServerSessionManager serverManager(UserRedisService userRedisService,
                                              Service service){
        return new ServerSessionManager(userRedisService, service);
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
