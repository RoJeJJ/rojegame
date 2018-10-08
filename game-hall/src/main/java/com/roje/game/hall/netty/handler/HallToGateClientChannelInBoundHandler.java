package com.roje.game.hall.netty.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.service.Service;

public class HallToGateClientChannelInBoundHandler extends DefaultInBoundHandler {
    public HallToGateClientChannelInBoundHandler(boolean containUid, Service service, MessageDispatcher dispatcher) {
        super(containUid, service, dispatcher);
    }
}
