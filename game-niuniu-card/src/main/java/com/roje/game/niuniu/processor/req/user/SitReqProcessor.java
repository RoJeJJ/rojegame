package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.processor.RoleCardRoomMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.SitRequest;
import com.roje.game.message.frame.Frame;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.data.NNCardRoom;
import org.springframework.stereotype.Component;


@Component
@TcpProcessor(action = Action.SitReq)
public class SitReqProcessor extends RoleCardRoomMessageProcessor<NNRole, NNCardRoom> {

    protected SitReqProcessor(MessageDispatcher dispatcher,
                              ISessionManager<NNRole> sessionManager,
                              CardRoomManager<NNRole, NNCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }

    @Override
    public void handlerRoleMessage(NNRole role, Frame frame) throws Exception {
        SitRequest request = frame.getData().unpack(SitRequest.class);
        roomManager.execute(role, room -> room.sit(role,request.getSeat()));
    }
}
