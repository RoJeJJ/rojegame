package com.roje.game.cluster;

import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.netty.NettyTcpServer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppCluster {
    public static void main(String[] args) {
        SpringApplication.run(AppCluster.class);
    }

    @Bean
    public ServerSessionManager serverManager(){
        return new ServerSessionManager();
    }


    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig() {
        return new ThreadConfig();
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
