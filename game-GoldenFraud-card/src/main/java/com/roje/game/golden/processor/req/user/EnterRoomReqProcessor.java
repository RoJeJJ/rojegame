package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultEnterCardRoomReqProcessor;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.EntryRoomReq_VALUE)
public class EnterRoomReqProcessor extends DefaultEnterCardRoomReqProcessor<GFCardRole> {
    protected EnterRoomReqProcessor(MessageDispatcher dispatcher,
                                    ISessionManager sessionManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
