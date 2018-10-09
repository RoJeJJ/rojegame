package com.roje.game.gate.processor.impl.game;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.login.LoginMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.roje.game.message.Mid.MID.LoginRes_VALUE;

@Slf4j
@Component
@Processor(mid = LoginRes_VALUE)
public class LoginRespProcessor extends MessageProcessor {
    private final GateUserSessionManager sessionManager;

    @Autowired
    public LoginRespProcessor(GateUserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        LoginMessage.LoginResponse response = LoginMessage.LoginResponse.parseFrom(bytes);
        String sessionId = response.getSessionId();
        GateUserSession session = sessionManager.getAnonymousSession(sessionId);
        if (session == null){
            log.warn("连接会话已重置或已登录");
            return;
        }
        if (response.getOk()){
            session.setRid(response.getRid());
            session.setUid(response.getUid());
            sessionManager.sessionLogin(session);
        }
        session.send(response);
    }
}
