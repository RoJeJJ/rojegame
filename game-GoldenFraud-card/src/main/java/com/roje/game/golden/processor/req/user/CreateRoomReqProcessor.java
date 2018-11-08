package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultCreateCardRoomReqProcessor;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.golden.data.GFRole;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.CreateCardRoomReq)
public class CreateRoomReqProcessor extends DefaultCreateCardRoomReqProcessor<GFRole, GFCardRoom> {
    protected CreateRoomReqProcessor(MessageDispatcher dispatcher,
                                     ISessionManager<GFRole> sessionManager,
                                     CardRoomManager<GFRole, GFCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
