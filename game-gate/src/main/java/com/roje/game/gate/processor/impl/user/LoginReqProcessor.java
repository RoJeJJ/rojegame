package com.roje.game.gate.processor.impl.user;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.gate.manager.GateUserSessionManager;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import com.roje.game.message.login.LoginMessage.LoginRequest;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

@Processor(mid = MID.LoginReq_VALUE)
public class LoginReqProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginReqProcessor.class);
    private GateUserSessionManager sessionManager;
    private BaseInfo gateInfo;

    public void setGateInfo(BaseInfo gateInfo) {
        this.gateInfo = gateInfo;
    }

    public void setSessionManager(GateUserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        LoginRequest request = LoginRequest.parseFrom(bytes);
        GateUserSession session = sessionManager.getSession(channel);
        if (session == null) {
            LOG.warn("连接会话已失效，请重连");
            channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErroCode.ConectReset, "连接会话已失效，请重连"));
            channel.close();
            return;
        }
        session.setVersionCode(request.getVersion());
        sessionManager.allocateServer(session);

        LoginRequest.Builder builder = request.toBuilder();
        builder.setSessionId(session.id());
        builder.setIp(((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress());
        builder.setGateId(gateInfo.getId());

        session.sendToHall(request.getMid().getNumber(),request.toByteArray());
    }
}
