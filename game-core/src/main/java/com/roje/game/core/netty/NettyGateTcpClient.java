package com.roje.game.core.netty;

import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.netty.channel.handler.DefaultInnerTcpClientChannelInBoundHandler;
import com.roje.game.core.netty.channel.initializer.DefaultChannelInitializer;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyGateTcpClient {


    private NettyConnGateClientConfig gateClientConfig;



    private Bootstrap bootstrap;

    private ChannelGroup channels;

    private final Service service;

    private final MessageDispatcher dispatcher;

    private final ISessionManager sessionManager;

    @Getter
    private final BaseInfo baseInfo;

    public NettyGateTcpClient(
            NettyConnGateClientConfig gateClientConfig,
            Service service,
            MessageDispatcher dispatcher,
            ISessionManager sessionManager,
            BaseInfo baseInfo){
        this.gateClientConfig = gateClientConfig;
        this.service = service;
        this.dispatcher = dispatcher;
        this.sessionManager = sessionManager;
        this.baseInfo = baseInfo;
        initClient();
    }

    private void initClient(){
        channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_LINGER, gateClientConfig.getSoLinger())
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, gateClientConfig.getConnectTimeout())
                .option(ChannelOption.TCP_NODELAY, gateClientConfig.isTcpNoDelay())
                .handler(new DefaultChannelInitializer() {
                    @Override
                    public void custom(ChannelPipeline pipeline) {
                        pipeline.addLast(new IdleStateHandler(gateClientConfig.getReaderIdleTime(),
                                gateClientConfig.getWriterIdleTime(),
                                gateClientConfig.getAllIdleTime()));
                        pipeline.addLast(new DefaultInnerTcpClientChannelInBoundHandler(
                                service,
                                dispatcher,
                                sessionManager,
                                baseInfo));
                    }
                });
    }

    public void connect(String ip,int port){
        try {
            ChannelFuture channelFuture = bootstrap.connect(ip,port).sync();
            channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
                if (channelFuture1.isSuccess()) {
                    channels.add(channelFuture1.channel());
                    log.info("成功连接Gate服务器[{}:{}]", ip, port);
                }else
                    log.info("连接Gate服务器[{}:{}]失败", ip, port);
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop(){
        channels.close();
    }


}
