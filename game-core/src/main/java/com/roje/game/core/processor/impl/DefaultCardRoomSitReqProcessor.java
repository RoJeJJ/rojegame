package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.create_room.SitRequest;
import com.roje.game.message.frame.Frame;


public class DefaultCardRoomSitReqProcessor<R extends CardRole> extends RoleMessageProcessor<R> {
    protected DefaultCardRoomSitReqProcessor(MessageDispatcher dispatcher,
                                             ISessionManager sessionManager) {
        super(dispatcher, sessionManager);
    }

    @Override
    public void handlerRoleMessage(R role, Frame frame) throws Exception {
        SitRequest request = frame.getData().unpack(SitRequest.class);
        role.roomExecute(room -> room.sit(role,request.getSeat()));
    }
}
