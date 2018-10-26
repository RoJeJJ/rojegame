package com.roje.game.niuniu;

import com.roje.game.core.config.ClusterClientConfig;
import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.netty.channel.initializer.ClusterClientChannelInitializer;
import com.roje.game.core.netty.channel.initializer.GameServerChannelInitializer;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.redis.service.RoomRedisService;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.niuniu.manager.NNRoomManager;
import com.roje.game.niuniu.manager.NNSessionManager;
import com.roje.game.niuniu.properties.NiuNiuProperties;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.IOException;

@SpringBootApplication
public class AppNN {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppNN.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public RoomRedisService roomRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new RoomRedisService(redisTemplate);
    }
    @Bean
    public ClusterClientConfig clusterClientConfig(){
        return new ClusterClientConfig();
    }

    @Bean
    public MessageDispatcher dispatcher(){
        return new MessageDispatcher();
    }


    @Bean ServerInfo serverInfo(){
        return new ServerInfo();
    }

    @Bean
    public ClusterClientChannelInitializer clusterClientChannelInitializer(
            ClusterClientConfig config,
            MessageDispatcher dispatcher,
            ISessionManager sessionManager,
            ServerInfo serverInfo){
        return new ClusterClientChannelInitializer(config,dispatcher,sessionManager,serverInfo);
    }

    @Bean
    public NettyClusterTcpClient clusterTcpClient(
            ClusterClientConfig config,
            ClusterClientChannelInitializer channelInitializer) {
        return new NettyClusterTcpClient(config, channelInitializer);
    }

    @Bean
    public NNRoomManager roomManager(NiuNiuProperties properties,
                                     RoomRedisService roomRedisService,
                                     ServerInfo serverInfo,
                                     Service service){
        return new NNRoomManager(properties,roomRedisService,serverInfo,service);
    }

    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new UserRedisService(redisTemplate);
    }

    @Bean
    public NNSessionManager sessionManager(UserRedisService userRedisService,
                                           NNRoomManager roomManager,
                                           AuthLock authLock){
        return new NNSessionManager(userRedisService,roomManager,authLock);
    }

    @Bean
    public ThreadProperties threadConfig(){
        return new ThreadProperties();
    }

    @Bean Service service(ThreadProperties config){
        return new Service(config);
    }

    @Bean
    public NettyServerConfig nettyTcpServerConfig(){
        return new NettyServerConfig();
    }

    @Bean
    public GameServerChannelInitializer gameServerChannelInitializer(
            NettyServerConfig config,
            Service service,
            SessionManager sessionManager,
            MessageDispatcher dispatcher){
        return new GameServerChannelInitializer(config,service,sessionManager,dispatcher);
    }

    @Bean(destroyMethod="shutdown")
    public RedissonClient redisson() throws IOException {
        return Redisson.create(Config.fromYAML(new ClassPathResource("redisson.yml").getInputStream()));
    }

    @Bean
    public AuthLock lock(RedissonClient client){
        return new AuthLock(client);
    }

    @Bean
    public NettyTcpServer userTcpServer(
            GameServerChannelInitializer channelInitializer,
            NettyServerConfig nettyServerConfig){
        return new NettyTcpServer(channelInitializer,nettyServerConfig);
    }
}
