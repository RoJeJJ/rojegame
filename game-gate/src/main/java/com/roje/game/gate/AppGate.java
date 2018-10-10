package com.roje.game.gate;


import com.roje.game.core.config.NettyConnClusterClientConfig;
import com.roje.game.core.config.NettyTcpServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.manager.UserManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.netty.channel.initializer.ConnClusterClientChannelInitializer;
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
    public NettyTcpServerConfig gateGameTcpConfig() {
        return new NettyTcpServerConfig();
    }

    @Bean
    public NettyConnClusterClientConfig gateClusterClientConfig(){
        return new NettyConnClusterClientConfig();
    }

    @Bean("gateInfo")
    @ConfigurationProperties(prefix = "server.info")
    public BaseInfo gateInfo(){
        return new BaseInfo();
    }

    @Bean
    public MessageDispatcher dispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public ServerManager serverManager(BaseInfo gateInfo){
        return new ServerManager(gateInfo);
    }

    @Bean
    public GateUserSessionManager sessionManager() {
        return new GateUserSessionManager();
    }

    @Bean
    public NettyTcpServer gateGameTcpServer(
            @Qualifier("gateGameTcpServerChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
            @Qualifier("gateGameTcpConfig") NettyTcpServerConfig nettyTcpServerConfig) {
        return new NettyTcpServer(channelInitializer, nettyTcpServerConfig);
    }

    @Bean
    public Service gateUserExecutorService(ThreadConfig config) {
        return new Service(config);
    }

    @Bean("gateClusterClientChannelInitializer")
    public ConnClusterClientChannelInitializer gateClusterClientChannelInitializer(NettyConnClusterClientConfig clusterClientConfig,
                                                                                   MessageDispatcher dispatcher,
                                                                                   UserManager userManager,
                                                                                   BaseInfo baseInfo){
        return new ConnClusterClientChannelInitializer(clusterClientConfig,dispatcher,userManager,baseInfo);
    }

    @Bean
    public NettyClusterTcpClient gateClusterClient(NettyConnClusterClientConfig clientConfig,
                                                   @Qualifier("gateClusterClientChannelInitializer") ChannelInitializer<SocketChannel> clientChannelInitializer){
        return new NettyClusterTcpClient(clientConfig,clientChannelInitializer);
    }
}
