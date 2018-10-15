package com.roje.game.gate.processor.resp.game;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.gate.manager.GateSessionSessionManager;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.Mid;
import com.roje.game.message.login.LoginMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Processor(mid = Mid.MID.LoginRes_VALUE)
public class LoginRespProcessor extends MessageProcessor {
    private final GateSessionSessionManager sessionManager;

    @Autowired
    public LoginRespProcessor(GateSessionSessionManager sessionManager) {
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
            long uid = response.getInfo().getId();
            log.info("{}登录成功",uid);
            session.setUid(uid);
            sessionManager.sessionLogin(session);
        }
        session.send(response);
    }
}
