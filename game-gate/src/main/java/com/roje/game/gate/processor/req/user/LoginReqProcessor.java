package com.roje.game.gate.processor.req.user;

import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Processor(action = Action.LoginReq)
public class LoginReqProcessor extends MessageProcessor {
    private final SessionManager<GateUserSession> sessionManager;
    private final BaseInfo gateInfo;

    @Autowired
    public LoginReqProcessor(SessionManager<GateUserSession> sessionManager,
                             BaseInfo gateInfo) {
        this.sessionManager = sessionManager;
        this.gateInfo = gateInfo;
    }


    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        GateUserSession session = sessionManager.getSession(channel);
        if (session != null)
            session.loginRequest(frame,gateInfo.getId());
    }
}
