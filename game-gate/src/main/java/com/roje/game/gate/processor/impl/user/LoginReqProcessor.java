package com.roje.game.gate.processor.impl.user;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerType;
import com.roje.game.gate.mannager.ServerManager;
import com.roje.game.gate.mannager.UserSessionManager;
import com.roje.game.gate.session.UserSession;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import com.roje.game.message.login.LoginMessage.LoginRequest;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Processor(mid = MID.LoginReq_VALUE)
public class LoginReqProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(LoginReqProcessor.class);
    @Autowired
    private UserSessionManager sessionManager;
    @Autowired
    private ServerManager serverManager;

    @Override
    public void handler(ChannelHandlerContext ctx, byte[] bytes) throws Exception{
        LoginRequest loginRequest = LoginRequest.parseFrom(bytes);
        UserSession userSession = sessionManager.getUserSessionById(ctx);
        if (userSession == null){
            LOG.warn("连接会话已失效，请重连");
            ctx.write(serverManager.errorResponse(CommonMessage.SystemErroCode.ConectReset,"连接会话已失效，请重连"));
            return;
        }
        ServerInfo hallInfo = serverManager.getIdleServer(ServerType.Hall,userSession);
        if (hallInfo == null){
            LOG.warn("未开启大厅服");
            ctx.write(serverManager.errorResponse(CommonMessage.SystemErroCode.HallNotFind,"未开启大厅服"));
            return;
        }
    }
}
