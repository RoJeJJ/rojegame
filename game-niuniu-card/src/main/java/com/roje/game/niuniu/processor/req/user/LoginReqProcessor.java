package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultLoginReqProcessor;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.message.action.Action;
import com.roje.game.niuniu.data.NNRole;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.LoginReq)
public class LoginReqProcessor extends DefaultLoginReqProcessor<NNRole> {
    public LoginReqProcessor(MessageDispatcher dispatcher, ISessionManager<NNRole> sessionManager, UserRedisService userRedisService) {
        super(dispatcher, sessionManager, userRedisService);
    }
}
