package com.roje.game.gate;


import com.roje.game.core.config.ClusterClientConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.netty.NettyClusterTcpClient;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.netty.channel.initializer.ClusterClientChannelInitializer;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateSessionManager;
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

    @Bean("gateUserTcpConfig")
    @ConfigurationProperties(prefix = "netty.config.gate-user-tcp")
    public NettyTcpServerConfig gateUserTcpConfig() {
        return new NettyTcpServerConfig();
    }

    @Bean
    public ClusterClientConfig gateClusterClientConfig() {
        return new ClusterClientConfig();
    }

    @Bean("gateInfo")
    @ConfigurationProperties(prefix = "server.info")
    public BaseInfo gateInfo() {
        return new BaseInfo();
    }

    @Bean
    public MessageDispatcher dispatcher() {
        return new MessageDispatcher();
    }

    @Bean
    public ServerManager serverManager(BaseInfo gateInfo) {
        return new ServerManager(gateInfo);
    }

    @Bean
    public SessionManager sessionManager(ServerManager serverManager) {
        return new GateSessionManager(serverManager);
    }

    @Bean
    public NettyTcpServer gateGameTcpServer(
            @Qualifier("gateGameTcpServerChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
            @Qualifier("gateGameTcpConfig") NettyTcpServerConfig nettyTcpServerConfig,
            BaseInfo gateInfo) {
        return new NettyTcpServer(channelInitializer, nettyTcpServerConfig, gateInfo.getInnerPort());
    }

    @Bean
    public NettyTcpServer getUserTcpServer(
            @Qualifier("gateUserTcpServerChannelInitializer") ChannelInitializer<SocketChannel> channelInitializer,
            @Qualifier("gateUserTcpConfig") NettyTcpServerConfig nettyTcpServerConfig,
            BaseInfo gateInfo) {
        return new NettyTcpServer(channelInitializer, nettyTcpServerConfig, gateInfo.getUserPort());
    }

    @Bean
    public Service gateUserExecutorService(ThreadConfig config) {
        return new Service(config);
    }

    @Bean("gateClusterClientChannelInitializer")
    public ClusterClientChannelInitializer gateClusterClientChannelInitializer(ClusterClientConfig clusterClientConfig,
                                                                               MessageDispatcher dispatcher,
                                                                               SessionManager sessionManager,
                                                                               BaseInfo baseInfo) {
        return new ClusterClientChannelInitializer(clusterClientConfig, dispatcher, sessionManager, baseInfo);
    }

    @Bean
    public NettyClusterTcpClient gateClusterClient(
            ClusterClientConfig clientConfig,
            @Qualifier("gateClusterClientChannelInitializer") ChannelInitializer<SocketChannel> clientChannelInitializer) {
        return new NettyClusterTcpClient(clientConfig, clientChannelInitializer);
    }
}
