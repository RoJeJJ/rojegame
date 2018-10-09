package com.roje.game.gate;


import com.roje.game.core.config.NettyClientConfig;
import com.roje.game.core.config.NettyServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.netty.NettyTcpClient;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateUserSessionManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppGate {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AppGate.class);
        app.setWebApplicationType(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    @ConfigurationProperties(prefix = "thread-config")
    public ThreadConfig threadConfig() {
        return new ThreadConfig();
    }

    @Bean("gateGameTcpConfig")
    @ConfigurationProperties(prefix = "netty.config.gate-game-tcp")
    public NettyServerConfig gateGameTcpConfig() {
        return new NettyServerConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "netty.config.gate-client")
    public NettyClientConfig getClientConfig(){
        return new NettyClientConfig();
    }

    @Bean
    @ConfigurationProperties(prefix = "gate-server.info")
    public BaseInfo baseInfo(){
        return new BaseInfo();
    }

    @Bean
    public MessageDispatcher dispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public ServerManager serverManager(){
        return new ServerManager();
    }

    @Bean
    public GateUserSessionManager sessionManager() {
        return new GateUserSessionManager();
    }

    @Bean
    public NettyTcpServer gateGameTcpServer(
            @Qualifier("gateGameTcpServerChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
            @Qualifier("gateGameTcpConfig") NettyServerConfig nettyServerConfig) {
        return new NettyTcpServer(channelInitializer, nettyServerConfig);
    }

    @Bean
    public Service gateUserExecutorService(ThreadConfig config) {
        return new Service(config);
    }

    @Bean
    public NettyTcpClient gateClusterClient(NettyClientConfig clientConfig,
                                            @Qualifier("gateClusterClientChannelInitializer") ChannelInitializer<SocketChannel> clientChannelInitializer){
        return new NettyTcpClient(clientConfig,clientChannelInitializer);
    }
}
