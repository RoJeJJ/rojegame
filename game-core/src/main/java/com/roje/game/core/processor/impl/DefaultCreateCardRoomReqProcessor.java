package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;

public class DefaultCreateCardRoomReqProcessor<R extends CardRole> extends RoleMessageProcessor<R> {
    protected DefaultCreateCardRoomReqProcessor(MessageDispatcher dispatcher,
                                                ISessionManager sessionManager) {
        super(dispatcher, sessionManager);
    }


    @Override
    public void handlerRoleMessage(R role, Frame frame) {
        role.createCardRoom(frame.getData());
    }
}
