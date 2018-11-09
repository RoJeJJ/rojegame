package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultCardRoomSitReqProcessor;
import com.roje.game.golden.data.GFCardRole;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.SitReq)
public class SitReqProcessor extends DefaultCardRoomSitReqProcessor<GFCardRole> {

    protected SitReqProcessor(MessageDispatcher dispatcher,
                              ISessionManager sessionManager) {
        super(dispatcher, sessionManager);
    }
}
