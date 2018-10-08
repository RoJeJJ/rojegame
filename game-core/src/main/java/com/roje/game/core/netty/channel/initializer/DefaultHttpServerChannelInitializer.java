package com.roje.game.core.netty.channel.initializer;

import com.roje.game.core.config.ServerConfig;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.List;

public class DefaultHttpServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    private List<ChannelHandler> handlers;
    private ServerConfig httpServerConfig;
    public DefaultHttpServerChannelInitializer(ServerConfig httpServerConfig){
        this.httpServerConfig = httpServerConfig;
    }
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new IdleStateHandler(httpServerConfig.getReaderIdleTime(),httpServerConfig.getWriterIdleTime(),httpServerConfig.getAllIdleTime()));
        pipeline.addLast(new HttpServerCodec());
        if (handlers != null && handlers.size() > 0){
            for (ChannelHandler handler:handlers)
                pipeline.addLast(handler);
        }
    }

    public void setHandlers(List<ChannelHandler> handlers) {
        this.handlers = handlers;
    }
}
