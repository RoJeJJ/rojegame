package com.roje.game.gate.processor.resp.game;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.InnerLoginResponse;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@TcpProcessor(action = Action.LoginRes)
public class LoginRespProcessorGame extends RoleMessageProcessor {
    private final SessionManager<GateUserSession> sessionManager;

    @Autowired
    public LoginRespProcessorGame(SessionManager<GateUserSession> sessionManager) {
        super(dispatcher, sessionManager);
        this.sessionManager = sessionManager;
    }


    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        InnerLoginResponse innerLoginResponse = frame.getData().unpack(InnerLoginResponse.class);
        String sessionId = innerLoginResponse.getSessionId();
        GateUserSession session = sessionManager.getSessionById(channel, sessionId);
        if (session != null){
            LoginResponse response = innerLoginResponse.getResponse();
            session.loginResponse(frame, response);
        }
    }
}
