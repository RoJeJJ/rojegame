package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.room.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.RoleCardRoomMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultEnterCardRoomReqProcessor;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.golden.data.GFCardRoom;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.EntryRoomReq)
public class EnterRoomReqProcessor extends DefaultEnterCardRoomReqProcessor<GFCardRole, GFCardRoom> {
    protected EnterRoomReqProcessor(MessageDispatcher dispatcher,
                                    ISessionManager<GFCardRole> sessionManager,
                                    CardRoomManager<GFCardRole, GFCardRoom> roomManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
