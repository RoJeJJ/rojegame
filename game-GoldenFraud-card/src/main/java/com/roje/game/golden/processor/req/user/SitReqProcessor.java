package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.Room;
import com.roje.game.message.create_room.SitRequest;
import com.roje.game.message.frame.Frame;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.SitReq)
public class SitReqProcessor extends RoleMessageProcessor<GFCardRole> {

    private final CardRoomManager<GFCardRole, GFCardRoom> roomManager;

    protected SitReqProcessor(MessageDispatcher dispatcher,
                              ISessionManager<GFCardRole> sessionManager,
                              CardRoomManager<GFCardRole, GFCardRoom> roomManager) {
        super(dispatcher, sessionManager);
        this.roomManager = roomManager;
    }

    @Override
    protected void handlerRoleMessage(GFCardRole role, Frame frame) throws Exception {
        SitRequest request = frame.getData().unpack(SitRequest.class);
        roomManager.roomExecute(role, room -> room.sit(role,request.getSeat()));
    }
}
