package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.service.Service;

public class DefaultConnGateTcpClientChannelInBoundHandler extends DefaultInBoundHandler {
    public DefaultConnGateTcpClientChannelInBoundHandler(Service service, MessageDispatcher dispatcher) {
        super(true, service, dispatcher);
    }
}
