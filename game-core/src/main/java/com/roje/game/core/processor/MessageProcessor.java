package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;



public abstract class MessageProcessor {
    private MessageDispatcher dispatcher;
    public void register(){
        dispatcher.register(this);
    }

    public void setDispatcher(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public abstract void handler(Channel channel, byte[] bytes)throws Exception;
}
