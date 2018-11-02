package com.roje.game.niuniu.processor.req.user;

import com.roje.game.core.exception.RJException;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import com.roje.game.message.frame.Frame;
import com.roje.game.niuniu.manager.NNSessionManager;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.CreateCardRoomReq,thread = ThreadType.def)
public class CreateCardRoomReqProcessor extends MessageProcessor {
    private final NNSessionManager sessionManager;

    @Autowired
    public CreateCardRoomReqProcessor(NNSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) {

    }
}
