package com.roje.game.core.netty;

import com.roje.game.core.config.ClusterClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


import javax.annotation.PostConstruct;

/**
 * 连接集群服务器的客户端
 */
@Slf4j
public class NettyClusterTcpClient implements Runnable{
    private EventLoopGroup group;
    private ClusterClientConfig clientConfig;
    private Bootstrap bootstrap;
    private Channel channel;
    private ChannelInitializer<SocketChannel> initializer;
    private int reconnectTime;

    public NettyClusterTcpClient(ClusterClientConfig clientConfig, ChannelInitializer<SocketChannel> initializer) {
        this.clientConfig = clientConfig;
        this.initializer = initializer;
        initClient();
        reconnectTime = clientConfig.getReconnectTime();
    }

    private void initClient() {
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(initializer)
                .remoteAddress(clientConfig.getIp(), clientConfig.getPort())
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
            ChannelFuture future = bootstrap.connect().awaitUninterruptibly();
            channel = future.channel();
            future.addListener(channelFuture -> {
                if (channelFuture.isSuccess())
                    log.info("成功连接Cluster服务器[{}:{}]", clientConfig.getIp(), clientConfig.getPort());
                else {
                    log.warn(String.format("连接Cluster服务器[%s:%d]失败", clientConfig.getIp(), clientConfig.getPort()));
                }
            });
            future.channel().closeFuture().sync();
            log.warn("连接Cluster的TCP 客户端已停止");
        }catch (Exception e){
//            reconnectTime = 0;
            log.warn("连接Cluster的TCP 客户端启动异常",e);
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
