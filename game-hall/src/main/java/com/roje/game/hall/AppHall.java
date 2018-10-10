package com.roje.game.hall;

import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ConnGateTcpMultiManager;
import com.roje.game.core.manager.UserManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.channel.initializer.ConnClusterClientChannelInitializer;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.hall.manager.HallUserManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppHall {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppHall.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig(){
        return new ThreadConfig();
    }

    @Bean
    public BaseInfo hallInfo(){
        return new BaseInfo();
    }

    @Bean
    public NettyConnClusterClientConfig hallClusterClientConfig(){
        return new NettyConnClusterClientConfig();
    }

    @Bean
    public NettyConnGateClientConfig nettyConnGateClientConfig(){
        return new NettyConnGateClientConfig();
    }

    @Bean
    public Service hallGateExecutorService(ThreadConfig config){
        return new Service(config);
    }

    @Bean
    public HallUserManager hallUserManager(){
        return new HallUserManager();
    }

    @Bean
    public MessageDispatcher messageDispatcher(){
        return new MessageDispatcher();
    }

    @Bean("hallClusterClientChannelInitializer")
    public ConnClusterClientChannelInitializer hallClusterClientChannelInitializer(NettyConnClusterClientConfig clusterClientConfig,
                                                                                   MessageDispatcher dispatcher,
                                                                                   UserManager userManager,
                                                                                   BaseInfo baseInfo){
        return new ConnClusterClientChannelInitializer(clusterClientConfig,dispatcher,userManager,baseInfo);
    }

    @Bean
    public NettyClusterTcpClient hallClusterTcpClient(NettyConnClusterClientConfig clusterClientConfig,
                                                      @Qualifier("hallClusterClientChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer){
        return new NettyClusterTcpClient(clusterClientConfig,channelInitializer);
    }

    @Bean
    public ConnGateTcpMultiManager nettyGateTcpMultiManager(NettyConnGateClientConfig nettyConnGateClientConfig,
                                                            Service service,
                                                            MessageDispatcher dispatcher){
        return new ConnGateTcpMultiManager(nettyConnGateClientConfig,service,dispatcher);
    }
}
