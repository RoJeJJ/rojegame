package com.roje.game.core.processor.impl;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleCardRoomMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.create_room.SitRequest;
import com.roje.game.message.frame.Frame;


public class DefaultCardRoomSitReqProcessor<R extends RoomRole,M extends CardRoom<R>> extends RoleCardRoomMessageProcessor<R,M> {
    protected DefaultCardRoomSitReqProcessor(MessageDispatcher dispatcher,
                                             ISessionManager<R> sessionManager,
                                             CardRoomManager<R, M> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }

    @Override
    public void handlerRoleMessage(R role, Frame frame) throws Exception {
        SitRequest request = frame.getData().unpack(SitRequest.class);
        roomManager.execute(role, room -> room.sit(role,request.getSeat()));
    }
}
