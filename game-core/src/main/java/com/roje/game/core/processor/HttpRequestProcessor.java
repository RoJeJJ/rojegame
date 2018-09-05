package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultHttpRequest;

public abstract class HttpRequestProcessor {
    private MessageDispatcher dispatcher;
    public void register(){
        dispatcher.register(this);
    }

    public void setDispatcher(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public abstract void handler(ChannelHandlerContext ctx, DefaultHttpRequest request);
}
