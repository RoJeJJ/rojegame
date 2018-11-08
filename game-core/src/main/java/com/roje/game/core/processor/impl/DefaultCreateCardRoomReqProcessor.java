package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleCardRoomMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;

public class DefaultCreateCardRoomReqProcessor extends RoleCardRoomMessageProcessor {
    protected DefaultCreateCardRoomReqProcessor(MessageDispatcher dispatcher,
                                                ISessionManager sessionManager,
                                                CardRoomManager roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }


    @Override
    public <R extends CardRole> void handlerRoleMessage(R role, Frame frame) throws Exception {
        roomManager.createRoom(role,frame.getData());
    }
}
