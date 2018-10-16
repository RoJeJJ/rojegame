package com.roje.game.hall;

import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ConnGateTcpMultiManager;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.channel.initializer.ConnClusterClientChannelInitializer;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.hall.manager.HallISessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication
public class AppHall {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppHall.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig() {
        return new ThreadConfig();
    }

    @Bean
    public BaseInfo hallInfo() {
        return new BaseInfo();
    }

    @Bean
    public NettyConnClusterClientConfig hallClusterClientConfig() {
        return new NettyConnClusterClientConfig();
    }

    @Bean
    public NettyConnGateClientConfig nettyConnGateClientConfig() {
        return new NettyConnGateClientConfig();
    }

    @Bean
    public Service hallGateExecutorService(ThreadConfig config) {
        return new Service(config);
    }

    @Bean
    public HallISessionManager hallUserManager() {
        return new HallISessionManager();
    }

    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean("hallClusterClientChannelInitializer")
    public ConnClusterClientChannelInitializer hallClusterClientChannelInitializer(NettyConnClusterClientConfig clusterClientConfig,
                                                                                   MessageDispatcher dispatcher,
                                                                                   ISessionManager ISessionManager,
                                                                                   BaseInfo baseInfo) {
        return new ConnClusterClientChannelInitializer(clusterClientConfig, dispatcher, ISessionManager, baseInfo);
    }

    @Bean
    public NettyClusterTcpClient hallClusterTcpClient(NettyConnClusterClientConfig clusterClientConfig,
                                                      @Qualifier("hallClusterClientChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer) {
        return new NettyClusterTcpClient(clusterClientConfig, channelInitializer);
    }

    @Bean
    public ConnGateTcpMultiManager nettyGateTcpMultiManager(
            NettyConnGateClientConfig nettyConnGateClientConfig,
            Service service,
            MessageDispatcher dispatcher,
            BaseInfo baseInfo,
            ISessionManager ISessionManager) {
        return new ConnGateTcpMultiManager(nettyConnGateClientConfig, service, dispatcher, baseInfo, ISessionManager);
    }

    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new UserRedisService(redisTemplate);
    }

    @Bean public IdService idService(
            StringRedisTemplate stringRedisTemplate){
        return new IdService(stringRedisTemplate);
    }
}
