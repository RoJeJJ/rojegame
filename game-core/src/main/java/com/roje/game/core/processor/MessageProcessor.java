package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public abstract class MessageProcessor {
    @Autowired
    private MessageDispatcher dispatcher;
    @PostConstruct
    public void register(){
        dispatcher.register(this);
    }

    public abstract void handler(Channel channel, byte[] bytes)throws Exception;
}
