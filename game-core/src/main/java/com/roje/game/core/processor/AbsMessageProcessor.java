package com.roje.game.core.processor;

import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import org.springframework.beans.factory.InitializingBean;

public abstract class AbsMessageProcessor implements MessageProcessor, InitializingBean {
    private final MessageDispatcher dispatcher;

    protected AbsMessageProcessor(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    public void afterPropertiesSet() {
        dispatcher.register(this);
    }
}
