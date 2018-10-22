package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.config.GameProperties;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.niuniu.data.NNRole;
import com.roje.game.niuniu.manager.NNSessionManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.CreateCardRoomReq)
public class CreateGoldRoomReqProcessor extends MessageProcessor {
    private final NNSessionManager sessionManager;

    private final GameProperties gameProperties;

    @Autowired
    public CreateGoldRoomReqProcessor(NNSessionManager sessionManager, GameProperties gameProperties) {
        this.sessionManager = sessionManager;
        this.gameProperties = gameProperties;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        if (sessionManager.isLogin(channel)){
            sessionManager.createRoom(channel,frame);
        }
    }
}
