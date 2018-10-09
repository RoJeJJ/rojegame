package com.roje.game.core.netty;

import com.roje.game.core.config.NettyClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


import javax.annotation.PostConstruct;

@Slf4j
public class NettyTcpClient implements Runnable{
    private EventLoopGroup group;
    private NettyClientConfig clientConfig;
    private Bootstrap bootstrap;
    private Channel channel;
    private ChannelInitializer<SocketChannel> initializer;
    private int reconnectTime;

    public NettyTcpClient(NettyClientConfig clientConfig, ChannelInitializer<SocketChannel> initializer) {
        this.clientConfig = clientConfig;
        this.initializer = initializer;
        initClient();
        reconnectTime = clientConfig.getReconnectTime();
    }

    private void initClient() {
        log.info("初始化连接{}服务器的客户端",clientConfig.getToCluster().getType().name());
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(initializer)
                .remoteAddress(clientConfig.getToCluster().getIp(), clientConfig.getToCluster().getPort())
                .option(ChannelOption.SO_LINGER, clientConfig.getSoLinger())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeout())
                .option(ChannelOption.TCP_NODELAY, clientConfig.isTcpNoDelay());
//            ChannelFuture channelFuture= bootstrap.connect().addListener(channelFutureListener);
//            channelFuture.channel().closeFuture().sync();
    }

    @PostConstruct
    public void start() {
        new Thread(this).start();
    }

    public void stop() {
        if (channel != null && channel.isActive())
            channel.close();
    }

    @Override
    public void run() {
        try {
            ChannelFuture future = bootstrap.connect().sync();
            channel = future.channel();
            future.addListener(channelFuture -> {
                if (channelFuture.isSuccess())
                    log.info("成功连接{}服务器[{}:{}]", clientConfig.getToCluster().getType().name(), clientConfig.getToCluster().getIp(), clientConfig.getToCluster().getPort());
                else {
                    log.warn(String.format("连接%s服务器[%s:%d]失败",  clientConfig.getToCluster().getType().name(), clientConfig.getToCluster().getIp(), clientConfig.getToCluster().getPort()));
                }
            });
            future.channel().closeFuture().sync();
            log.warn("TCP 客户端已停止");
        }catch (Exception e){
//            reconnectTime = 0;
            log.warn("TCP 客户端启动异常",e);
        }finally {
            if (reconnectTime > 0){
                log.info("{}秒后重新连接", reconnectTime);
                try {
                    Thread.sleep(reconnectTime * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                run();
            }else
                group.shutdownGracefully();
        }
    }
}
