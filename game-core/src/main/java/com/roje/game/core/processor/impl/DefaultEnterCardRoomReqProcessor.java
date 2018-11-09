package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.create_room.EntryRoomRequest;
import com.roje.game.message.frame.Frame;

public class DefaultEnterCardRoomReqProcessor<R extends CardRole> extends RoleMessageProcessor<R> {

    protected DefaultEnterCardRoomReqProcessor(MessageDispatcher dispatcher,
                                               ISessionManager sessionManager) {
        super(dispatcher, sessionManager);
    }

    @Override
    public void handlerRoleMessage(R role, Frame frame) throws Exception {
        EntryRoomRequest request = frame.getData().unpack(EntryRoomRequest.class);
        role.joinRoom(request.getRoomId());
    }
}
