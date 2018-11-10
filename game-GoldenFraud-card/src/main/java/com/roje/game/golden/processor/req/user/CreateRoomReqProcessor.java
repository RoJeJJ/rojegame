package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultCreateCardRoomReqProcessor;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.CreateCardRoomReq_VALUE)
public class CreateRoomReqProcessor extends DefaultCreateCardRoomReqProcessor {
    protected CreateRoomReqProcessor(MessageDispatcher dispatcher,
                                     ISessionManager sessionManager) {
        super(dispatcher, sessionManager, roomManager);
    }
}
