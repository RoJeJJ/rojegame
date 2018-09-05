package com.roje.game.core.netty;

import com.roje.game.core.config.ClientConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


public class NettyTcpClient implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(NettyTcpClient.class);
    private EventLoopGroup group;
    private ClientConfig clientConfig;
    private Bootstrap bootstrap;
    private Channel channel;
    private ChannelInitializer<SocketChannel> initializer;
    private int reconnectTime;

    public NettyTcpClient(ClientConfig clientConfig, ChannelInitializer<SocketChannel> initializer) {
        this.clientConfig = clientConfig;
        this.initializer = initializer;
        initClient();
        reconnectTime = clientConfig.getReconnectTime();
    }

    private void initClient() {
        LOG.info("初始化连接{}服务器的客户端",clientConfig.getConnToType().name());
        group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(initializer)
                .remoteAddress(clientConfig.getHost(), clientConfig.getPort())
                .option(ChannelOption.SO_LINGER, clientConfig.getSoLinger())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, clientConfig.getConnectTimeout())
                .option(ChannelOption.TCP_NODELAY, clientConfig.isTcpNoDelay());
//            ChannelFuture channelFuture= bootstrap.connect().addListener(channelFutureListener);
//            channelFuture.channel().closeFuture().sync();
        LOG.info("初始化完成");
    }

    public void start() {
        new Thread(this).start();
    }

    public void shutDown() {
        if (channel != null)
            channel.close();
    }

    public ClientConfig getClientConfig() {
        return clientConfig;
    }

    @Override
    public void run() {
        try {
            ChannelFuture future = bootstrap.connect().sync();
//            channel = future.channel();
            future.addListener(channelFuture -> {
                if (channelFuture.isSuccess())
                    LOG.info("成功连接{}服务器[{}:{}]", clientConfig.getConnToType().name(), clientConfig.getHost(), clientConfig.getPort());
                else {
                    LOG.warn(String.format("连接%s服务器[%s:%d]失败", clientConfig.getConnToType().name(), clientConfig.getHost(), clientConfig.getPort()));
                }
            });
            future.channel().closeFuture().sync();
            LOG.warn("TCP 客户端已停止");
        }catch (Exception e){
//            reconnectTime = 0;
            LOG.warn("TCP 客户端启动异常",e);
        }finally {
            if (reconnectTime > 0){
                LOG.info("{}秒后重新连接", reconnectTime);
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
