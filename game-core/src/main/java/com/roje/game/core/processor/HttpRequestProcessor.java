package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

public abstract class HttpRequestProcessor {
    @Autowired
    private MessageDispatcher dispatcher;
    @PostConstruct
    public void register(){
        dispatcher.register(this);
    }

    public abstract void handler(ChannelHandlerContext ctx, DefaultHttpRequest request);
}
