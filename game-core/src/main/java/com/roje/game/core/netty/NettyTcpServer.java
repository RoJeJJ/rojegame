package com.roje.game.core.netty;

import com.roje.game.core.config.ServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class NettyTcpServer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(NettyTcpServer.class);
    private ChannelInitializer<SocketChannel> initializer;
    private ServerConfig serverConfig;
    private Channel channel;

    public NettyTcpServer(ChannelInitializer<SocketChannel> initializer,ServerConfig serverConfig){
        this.initializer = initializer;
        this.serverConfig = serverConfig;
    }

    @Override
    public synchronized void run() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer)
                    .option(ChannelOption.SO_BACKLOG, serverConfig.getSoBackLog())
                    .childOption(ChannelOption.TCP_NODELAY, serverConfig.isTcpNoDelay())
                    .childOption(ChannelOption.SO_KEEPALIVE, serverConfig.isSoKeepAlive())
                    .childOption(ChannelOption.SO_LINGER, serverConfig.getSoLinger());
            ChannelFuture channelFuture = serverBootstrap.bind(serverConfig.getPort()).sync().addListener(channelFuture1 -> {
                if (channelFuture1.isSuccess())
                    LOG.info("TCP服务器已启动,监听端口:" + serverConfig.getPort());
                else
                    LOG.info("TCP服务器启动失败");
            });
            channel = channelFuture.channel();
            channel.closeFuture().sync();
            LOG.info("TCP服务器已停止");
        } catch (Exception e) {
            LOG.error("TCP服务器启动异常", e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    public void start(){
        new Thread(this).start();
    }

    public void stop(){
        if (channel != null)
            channel.close();
    }

    public ServerConfig getServerConfig(){
        return serverConfig;
    }
}
