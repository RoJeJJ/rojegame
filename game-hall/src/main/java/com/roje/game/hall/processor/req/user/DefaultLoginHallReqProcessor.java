package com.roje.game.hall.processor.req.user;

import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.impl.DefaultLoginReqProcessor;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.hall.entity.HRole;
import com.roje.game.message.action.Action;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.LoginReq)
public class DefaultLoginHallReqProcessor extends DefaultLoginReqProcessor<HRole> {
    public DefaultLoginHallReqProcessor(MessageDispatcher dispatcher,
                                        ISessionManager<HRole> sessionManager,
                                        UserRedisService userRedisService) {
        super(dispatcher, sessionManager, userRedisService);
    }
}
