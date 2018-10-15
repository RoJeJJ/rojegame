package com.roje.game.core.processor;

import com.roje.game.core.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;


public abstract class MessageProcessor<MSG> {
    @Setter@Getter
    protected long uid;
    @Autowired
    private MessageDispatcher dispatcher;
    @PostConstruct
    public void register(){
        dispatcher.register(this);
    }

    public abstract void handler(Channel channel, MSG msg)throws Exception;
}
