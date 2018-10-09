package com.roje.game.core.netty;

import com.roje.game.core.config.NettyServerConfig;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

public class NettyHttpServer implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(NettyHttpServer.class);
    private ChannelInitializer<SocketChannel> initializer;
    private NettyServerConfig nettyServerConfig;
    private Channel channel;
    public NettyHttpServer(ChannelInitializer<SocketChannel> initializer,NettyServerConfig nettyServerConfig){
        this.initializer = initializer;
        this.nettyServerConfig = nettyServerConfig;
    }
    @Override
    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(boss,worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer)
                    .option(ChannelOption.SO_BACKLOG, nettyServerConfig.getSoBackLog())
                    .childOption(ChannelOption.TCP_NODELAY, nettyServerConfig.isTcpNoDelay())
                    .childOption(ChannelOption.SO_KEEPALIVE, nettyServerConfig.isSoKeepAlive())
                    .childOption(ChannelOption.SO_LINGER, nettyServerConfig.getSoLinger());
            ChannelFuture channelFuture = bootstrap.bind(nettyServerConfig.getPort()).sync().addListener(channelFuture1 -> {
                if (channelFuture1.isSuccess())
                    LOG.info("HTTP服务器已启动,监听端口:" + nettyServerConfig.getPort());
                else
                    LOG.error("HTTP服务器启动失败");
            });
            channel = channelFuture.channel();
            channel.closeFuture().sync();
            LOG.info("hTTP服务器已停止");
        }catch (Exception e){
            LOG.error("HTTP服务器启动异常",e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
    public void stop(){
        if (channel != null)
            channel.close();
    }
    @PostConstruct
    public void start(){
        new Thread(this).start();
    }
}
