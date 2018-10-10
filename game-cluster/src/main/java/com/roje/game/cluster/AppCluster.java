package com.roje.game.cluster;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.NettyTcpServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.netty.NettyHttpServer;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.server.BaseInfo;
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
@EnableConfigurationProperties(value = NettyTcpServerConfig.class)
public class AppCluster {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppCluster.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public BaseInfo clusterInfo(){
        return new BaseInfo();
    }

    @Bean
    public ServerManager serverManager(BaseInfo clusterInfo){
        return new ServerManager(clusterInfo);
    }


    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig(){
        return new ThreadConfig();
    }

    @Bean("clusterTcpServerConfig")
    @ConfigurationProperties(prefix = "netty-cluster-tcp-server-config")
    public NettyTcpServerConfig clusterTcpServerConfig(){
        return new NettyTcpServerConfig();
    }

    @Bean("clusterHttpServerConfig")
    @ConfigurationProperties(prefix = "netty-cluster-http-server-config")
    public NettyServerConfig clusterHttpServerConfig(){
        return new NettyServerConfig();
    }

    @Bean
    public Service clusterTcpExecutorService(ThreadConfig config){
        return new Service(config);
    }

    @Bean
    public NettyHttpServer clusterHttpServer(@Qualifier("clusterHttpChannelInitializer")ChannelInitializer<SocketChannel> channelInitializer,
                                           @Qualifier("clusterHttpServerConfig")NettyServerConfig clusterHttpServerConfig){
        return new NettyHttpServer(channelInitializer, clusterHttpServerConfig);
    }

    @Bean
    public NettyTcpServer clusterTcpServer(@Qualifier("clusterTcpChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
                                         @Qualifier("clusterTcpServerConfig") NettyTcpServerConfig nettyTcpServerConfig){
        return new NettyTcpServer(channelInitializer, nettyTcpServerConfig);
    }

    @Bean
    public MessageDispatcher messageDispatcher(){
        return new MessageDispatcher();
    }
}
