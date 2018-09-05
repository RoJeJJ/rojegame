package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.ClientConfig;
import com.roje.game.core.netty.channel.codec.DefaultMessageCodec;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

public class DefaultClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private List<ChannelHandler> handlers;
    private ClientConfig gateConfig;
    public DefaultClientChannelInitializer(ClientConfig gateConfig){
        this.gateConfig = gateConfig;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(gateConfig.getReaderIdleTime(),gateConfig.getWriterIdleTime(),gateConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageCodec());
        for (ChannelHandler handler:handlers){
            pipeline.addLast(handler);
        }
    }

    public void setHandlers(List<ChannelHandler> handlers) {
        this.handlers = handlers;
    }
}
