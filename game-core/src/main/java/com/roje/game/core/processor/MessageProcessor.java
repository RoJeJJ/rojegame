package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public abstract class MessageProcessor {
    @Autowired
    private MessageDispatcher dispatcher;
    @PostConstruct
    public void register(){
        dispatcher.register(this);
    }

    public abstract void handler(Channel channel, Frame frame)throws Exception;
}
