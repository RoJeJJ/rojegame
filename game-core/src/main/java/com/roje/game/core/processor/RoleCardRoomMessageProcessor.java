package com.roje.game.core.processor;

import com.roje.game.core.entity.role.RoomRole;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;

public abstract class RoleCardRoomMessageProcessor<R extends RoomRole,M extends CardRoom<R>> extends RoleMessageProcessor<R> {
    protected final CardRoomManager<R,M> roomManager;
    protected RoleCardRoomMessageProcessor(MessageDispatcher dispatcher,
                                           ISessionManager<R> sessionManager,
                                           CardRoomManager<R, M> roomManager) {
        super(dispatcher, sessionManager);
        this.roomManager = roomManager;
    }

    @Override
    public abstract void handlerRoleMessage(R role, Frame frame) throws Exception;
}
