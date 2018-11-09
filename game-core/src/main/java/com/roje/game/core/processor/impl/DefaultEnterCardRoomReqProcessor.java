package com.roje.game.core.processor.impl;

import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.entity.room.impl.CardRoom;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.create_room.EntryRoomRequest;
import com.roje.game.message.frame.Frame;

public class DefaultEnterCardRoomReqProcessor<R extends CardRole> extends RoleMessageProcessor<R> {

    private final CardRoomManager<R,? extends CardRoom> roomManager;

    protected DefaultEnterCardRoomReqProcessor(MessageDispatcher dispatcher,
                                               ISessionManager<R> sessionManager,
                                               CardRoomManager<R, ? extends CardRoom> roomManager) {
        super(dispatcher, sessionManager);
        this.roomManager = roomManager;
    }

    @Override
    public void handlerRoleMessage(R role, Frame frame) throws Exception {
        EntryRoomRequest request = frame.getData().unpack(EntryRoomRequest.class);
        roomManager.joinRoom(role,request.getRoomId());
    }
}
