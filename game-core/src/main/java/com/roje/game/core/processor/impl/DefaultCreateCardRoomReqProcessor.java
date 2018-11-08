package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.RoomRole;
import com.roje.game.core.entity.room.CardRoom;
import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleCardRoomMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;

public class DefaultCreateCardRoomReqProcessor<R extends RoomRole,M extends CardRoom<R>> extends RoleCardRoomMessageProcessor<R,M> {
    protected DefaultCreateCardRoomReqProcessor(MessageDispatcher dispatcher,
                                                ISessionManager<R> sessionManager,
                                                CardRoomManager<R, M> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }

    @Override
    public void handlerRoleMessage(R role, Frame frame) {
        roomManager.createRoom(role,frame.getData());
    }
}
