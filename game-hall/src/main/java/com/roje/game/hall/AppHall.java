package com.roje.game.hall;

import com.roje.game.core.config.ClusterClientConfig;
import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.config.ThreadProperties;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.NettyGateTcpClient;
import com.roje.game.core.netty.channel.initializer.ClusterClientChannelInitializer;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.thread.executor.RJExecutor;
import com.roje.game.hall.manager.HallUserManager;
import com.roje.game.hall.service.UserService;
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
    public ThreadProperties threadConfig() {
        return new ThreadProperties();
    }

    @Bean
    public BaseInfo hallInfo() {
        return new BaseInfo();
    }

    @Bean
    public ClusterClientConfig hallClusterClientConfig() {
        return new ClusterClientConfig();
    }

    @Bean
    public NettyConnGateClientConfig nettyConnGateClientConfig() {
        return new NettyConnGateClientConfig();
    }

    @Bean
    public Service hallGateExecutorService(ThreadProperties config) {
        return new Service(config);
    }

    @Bean
    public HallUserManager hallUserManager(
            UserService userService) {
        return new HallUserManager(userService, idService);
    }


    @Bean
    public MessageDispatcher messageDispatcher() {
        return new MessageDispatcher();
    }

    @Bean("hallClusterClientChannelInitializer")
    public ClusterClientChannelInitializer hallClusterClientChannelInitializer(ClusterClientConfig clusterClientConfig,
                                                                               MessageDispatcher dispatcher,
                                                                               ISessionManager ISessionManager,
                                                                               BaseInfo baseInfo) {
        return new ClusterClientChannelInitializer(clusterClientConfig, dispatcher, ISessionManager, baseInfo);
    }

    @Bean
    public NettyClusterTcpClient hallClusterTcpClient(
            ClusterClientConfig clusterClientConfig,
            @Qualifier("hallClusterClientChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer) {
        return new NettyClusterTcpClient(clusterClientConfig, channelInitializer);
    }

    @Bean
    public UserRedisService userRedisService(RedisTemplate<Object,Object> redisTemplate){
        return new UserRedisService(redisTemplate);
    }

    @Bean public IdService idService(
            StringRedisTemplate stringRedisTemplate){
        return new IdService(stringRedisTemplate);
    }

    @Bean
    public NettyGateTcpClient hallGateTcpClient(
            NettyConnGateClientConfig nettyConnGateClientConfig,
            Service service,
            MessageDispatcher dispatcher,
            ISessionManager sessionManager,
            BaseInfo hallInfo){
        return new NettyGateTcpClient(nettyConnGateClientConfig,service,dispatcher,sessionManager,hallInfo);
    }

    @Bean
    public RJExecutor userExecutor(){
        return new RJExecutor(3,"user");
    }
}
