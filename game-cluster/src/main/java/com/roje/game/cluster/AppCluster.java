package com.roje.game.cluster;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.netty.NettyHttpServer;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(value = NettyServerConfig.class)
public class AppCluster {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppCluster.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public ServerManager serverManager(){
        return new ServerManager();
    }


    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig(){
        return new ThreadConfig();
    }

    @Bean("clusterNettyTcpConfig")
    @ConfigurationProperties(prefix = "netty.config.cluster-tcp")
    public NettyServerConfig clusterNettyTcpConfig(){
        return new NettyServerConfig();
    }

    @Bean("clusterNettyHttpConfig")
    @ConfigurationProperties(prefix = "netty.config.cluster-http")
    public NettyServerConfig clusterNettyHttpConfig(){
        return new NettyServerConfig();
    }

    @Bean
    public Service clusterTcpExecutorService(ThreadConfig config){
        return new Service(config);
    }

    @Bean
    public NettyHttpServer nettyHttpServer(@Qualifier("clusterHttpChannelInitializer")ChannelInitializer<SocketChannel> channelInitializer,
                                           @Qualifier("clusterNettyHttpConfig")NettyServerConfig nettyServerConfig){
        return new NettyHttpServer(channelInitializer, nettyServerConfig);
    }

    @Bean
    public NettyTcpServer nettyTcpServer(@Qualifier("clusterTcpChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
                                         @Qualifier("clusterNettyTcpConfig") NettyServerConfig nettyServerConfig){
        return new NettyTcpServer(channelInitializer, nettyServerConfig);
    }

    @Bean
    public MessageDispatcher messageDispatcher(){
        return new MessageDispatcher();
    }
}
