package com.roje.game.gate.processor.req.user;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@TcpProcessor(action = Action.LoginReq)
public class LoginReqProcessorGame extends RoleMessageProcessor {
    private final SessionManager<GateUserSession> sessionManager;
    private final BaseInfo gateInfo;

    @Autowired
    public LoginReqProcessorGame(SessionManager<GateUserSession> sessionManager,
                                 BaseInfo gateInfo) {
        super(dispatcher, sessionManager);
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
