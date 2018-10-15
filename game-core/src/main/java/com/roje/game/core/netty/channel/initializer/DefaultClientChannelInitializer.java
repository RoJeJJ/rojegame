package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.ClientConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

public class DefaultClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    private List<ChannelHandler> handlers;
    private ClientConfig clientConfig;
    public DefaultClientChannelInitializer(ClientConfig clientConfig){
        this.clientConfig = clientConfig;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(clientConfig.getReaderIdleTime(),clientConfig.getWriterIdleTime(),clientConfig.getAllIdleTime()));
        pipeline.addLast(new DefaultMessageDecoder());
        for (ChannelHandler handler:handlers){
            pipeline.addLast(handler);
        }
    }

    public void setHandlers(List<ChannelHandler> handlers) {
        this.handlers = handlers;
    }
}
