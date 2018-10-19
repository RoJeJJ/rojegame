package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.CreateCardRoomReq)
public class CreateGoldRoomReqProcessor extends MessageProcessor {
    private final SessionManager sessionManager;

    @Autowired
    public CreateGoldRoomReqProcessor(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        if (sessionManager.isLogin(channel)){

        }
    }
}
