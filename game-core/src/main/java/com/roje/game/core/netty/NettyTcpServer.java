package com.roje.game.core.netty;

import com.roje.game.core.config.NettyTcpServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

@Slf4j
public class NettyTcpServer implements Runnable {
    private ChannelInitializer<SocketChannel> initializer;
    private NettyTcpServerConfig nettyTcpServerConfig;
    private Channel channel;

    public NettyTcpServer(ChannelInitializer<SocketChannel> initializer,NettyTcpServerConfig nettyTcpServerConfig){
        this.initializer = initializer;
        this.nettyTcpServerConfig = nettyTcpServerConfig;
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
                    .option(ChannelOption.SO_BACKLOG, nettyTcpServerConfig.getSoBackLog())
                    .childOption(ChannelOption.TCP_NODELAY, nettyTcpServerConfig.isTcpNoDelay())
                    .childOption(ChannelOption.SO_KEEPALIVE, nettyTcpServerConfig.isSoKeepAlive())
                    .childOption(ChannelOption.SO_LINGER, nettyTcpServerConfig.getSoLinger());

            ChannelFuture channelFuture = serverBootstrap.bind(nettyTcpServerConfig.getPort()).sync().addListener(channelFuture1 -> {
                if (channelFuture1.isSuccess())
                    log.info("TCP服务器已启动,监听端口:" + nettyTcpServerConfig.getPort());
                else
                    log.info("TCP服务器启动失败");
            });
            channel = channelFuture.channel();
            channel.closeFuture().sync();
            log.info("TCP服务器已停止");
        } catch (Exception e) {
            log.error("TCP服务器启动异常", e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    @PostConstruct
    public void start(){
        new Thread(this).start();
    }

    public void stop(){
        if (channel != null)
            channel.close();
    }
}
