package com.roje.game.gate.processor.impl.user;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.core.util.TimeUtil;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import com.roje.game.message.common.CommonMessage.HeartBeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(mid = MID.HeartReq_VALUE)
public class HeartBeatProcessor extends MessageProcessor {

    private final GateUserSessionManager sessionManager;

    @Autowired
    public HeartBeatProcessor(GateUserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void handler(Channel channel, byte[] bytes) {
        GateUserSession session = sessionManager.getSession(channel);
        if (session == null){
            log.info("连接会话已失效,请重新连接");
            channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErroCode.ConectReset,"连接会话已失效,请重新连接"));
            channel.close();
            return;
        }
        HeartBeatResponse.Builder builder = HeartBeatResponse.newBuilder();
        builder.setServerTime(TimeUtil.currentTimeMillis());
        session.send(builder.build());
    }
}
