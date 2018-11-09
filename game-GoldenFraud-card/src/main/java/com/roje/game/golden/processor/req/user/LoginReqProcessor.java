package com.roje.game.golden.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultLoginReqProcessor;
import com.roje.game.core.redis.service.UserRedisService;
import org.springframework.stereotype.Component;

@Component
public class LoginReqProcessor extends DefaultLoginReqProcessor {
    public LoginReqProcessor(MessageDispatcher dispatcher,
                             ISessionManager sessionManager,
                             UserRedisService userRedisService) {
        super(dispatcher, sessionManager, userRedisService);
    }
}
