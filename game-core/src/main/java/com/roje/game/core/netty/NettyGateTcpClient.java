package com.roje.game.core.netty;

import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.manager.ConnGateTcpMultiManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpClientChannelInBoundHandler;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.message.conn_info.ConnInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyGateTcpClient implements Runnable {

    private Channel channel;

    private NettyConnGateClientConfig gateClientConfig;

    private ConnInfo connInfo;

    private ConnGateTcpMultiManager manager;

    public NettyGateTcpClient(ConnGateTcpMultiManager manager,
                              NettyConnGateClientConfig gateClientConfig,
                              ConnInfo connInfo){
        this.gateClientConfig = gateClientConfig;
        this.connInfo = connInfo;
        this.manager = manager;
    }

    @Override
    public void run() {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(connInfo.getIp(),connInfo.getPort())
                    .option(ChannelOption.SO_LINGER, gateClientConfig.getSoLinger())
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, gateClientConfig.getConnectTimeout())
                    .option(ChannelOption.TCP_NODELAY, gateClientConfig.isTcpNoDelay())
                    .handler(new DefaultChannelInitializer() {
                        @Override
                        public void custom(ChannelPipeline pipeline) throws Exception {
                            pipeline.addLast(new IdleStateHandler(gateClientConfig.getReaderIdleTime(),
                                    gateClientConfig.getWriterIdleTime(),
                                    gateClientConfig.getAllIdleTime()));
                            pipeline.addLast(new DefaultInnerTcpClientChannelInBoundHandler(
                                    manager.getService(),
                                    manager.getDispatcher(),
                                    manager.getISessionManager(),
                                    manager.getBaseInfo()));
                        }
                    });
            ChannelFuture channelFuture = bootstrap.connect().sync();
            channel = channelFuture.channel();
            channelFuture.addListener(future -> {
                if (future.isSuccess()) {
                    manager.addClient(this);
                    log.info("成功连接Gate服务器[{}:{}]", connInfo.getIp(), connInfo.getPort());
                }else
                    log.info("连接Gate服务器[{}:{}]失败", connInfo.getIp(), connInfo.getPort());
            });
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            log.warn("连接网关出现异常",e);
        }finally {
            manager.removeClient(connInfo.getId());
            group.shutdownGracefully();
        }
    }

    public ConnInfo getConnInfo() {
        return connInfo;
    }

    public Channel getChannel() {
        return channel;
    }

    public void stop(){
        if (channel != null && channel.isActive())
            channel.close();
    }

    public void start(){
        new Thread(this).start();
    }
}
