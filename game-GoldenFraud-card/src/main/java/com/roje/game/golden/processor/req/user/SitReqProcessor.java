package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultCardRoomSitReqProcessor;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.SitReq)
public class SitReqProcessor extends DefaultCardRoomSitReqProcessor<GFCardRole, GFCardRoom> {

    protected SitReqProcessor(MessageDispatcher dispatcher,
                              ISessionManager<GFCardRole> sessionManager,
                              CardRoomManager<GFCardRole, GFCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
