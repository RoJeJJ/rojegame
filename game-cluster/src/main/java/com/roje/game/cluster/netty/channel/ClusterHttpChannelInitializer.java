package com.roje.game.cluster.netty.channel;

import com.roje.game.core.netty.channel.handler.DefaultHttpInBoundHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.stereotype.Component;

@Component("clusterHttpChannelInitializer")
public class ClusterHttpChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new DefaultHttpInBoundHandler());
    }
}
