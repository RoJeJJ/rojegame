package com.roje.game.gate.processor.req.user;

import com.google.protobuf.Any;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.util.TimeUtil;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.heart_beat.HeartBeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.HeartBeatReq)
public class HeartBeatProcessor extends MessageProcessor {

    private final SessionManager<GateUserSession> sessionManager;

    @Autowired
    public HeartBeatProcessor(SessionManager<GateUserSession> sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void handler(Channel channel, Frame frame) {
        GateUserSession session = sessionManager.getSession(channel);
        if (session != null){
            Frame.Builder b = frame.toBuilder();
            b.setAction(Action.HeartBeatRes);
            HeartBeatResponse.Builder builder = HeartBeatResponse.newBuilder();
            builder.setTime(TimeUtil.currentTimeMillis());
            b.setData(Any.pack(builder.build()));
            session.send(b.build());
        }
    }
}
