package com.roje.game.gate.processor.req.user;

import com.google.protobuf.Any;
import com.roje.game.core.manager.session.SessionManager;
import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
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
@TcpProcessor(action = Action.HeartBeatReq)
public class RoleMessageProcessor extends RoleMessageProcessor {

    private final SessionManager<GateUserSession> sessionManager;

    @Autowired
    public RoleMessageProcessor(
            SessionManager<GateUserSession> sessionManager) {
        super(dispatcher, sessionManager);
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
