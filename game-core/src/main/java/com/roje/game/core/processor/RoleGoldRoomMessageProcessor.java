package com.roje.game.core.processor;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;

public abstract class RoleGoldRoomMessageProcessor<R extends RoomRole,M extends Room<R>> extends RoleMessageProcessor<R> {

    protected final GoldRoomManager<R,M> roomManager;

    protected RoleGoldRoomMessageProcessor(MessageDispatcher dispatcher,
                                           ISessionManager<R> sessionManager,
                                           GoldRoomManager<R, M> roomManager) {
        super(dispatcher, sessionManager);
        this.roomManager = roomManager;
    }
}
