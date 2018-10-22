package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.LoginRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 登录牛牛游戏服务器
 */
@Slf4j
@Component
@Processor(action = Action.LoginReq,thread = ThreadType.def)
public class LoginReqProcessor extends MessageProcessor {

    private final SessionManager sessionManager;

    @Autowired
    public LoginReqProcessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        LoginRequest loginRequest = frame.getData().unpack(LoginRequest.class);

        sessionManager.login(channel,loginRequest);
    }
}
