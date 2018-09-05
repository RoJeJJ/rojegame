package com.roje.game.gate.netty.handler;

import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.gate.mannager.UserSessionManager;
import com.roje.game.gate.session.UserSession;
import com.roje.game.model.constant.Reason;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class GateUserServerChannelInBoundHandler extends DefaultInBoundHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GateUserServerChannelInBoundHandler.class);
    private UserSessionManager sessionManager;

    private static final AttributeKey<UserSession> USER_SESSION_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.user.session");
    private static final AttributeKey<Reason> REASON_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.close.reason");

    public void setSessionManager(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     *
     * @param ctx
     * @param bytes
     */
    public void forward(ChannelHandlerContext ctx,byte[] bytes){

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        UserSession session = new UserSession(ctx);
        ctx.channel().attr(USER_SESSION_ATTRIBUTE_KEY).set(session);
        sessionManager.userConnected(session);
        LOG.info("channelActive:{}",session.getId());
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception{
        UserSession session = ctx.channel().attr(USER_SESSION_ATTRIBUTE_KEY).getAndSet(null);
        if (session != null) {
            Reason reason = ctx.channel().attr(REASON_ATTRIBUTE_KEY).getAndSet(null);
            reason = reason == null?Reason.UnKnown:reason;
            sessionManager.remove(session,reason);
            LOG.info("userSession remove:{},reason:{}",session.getId(),reason.getReason());
        }
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    LOG.warn("{} reader timeout,close it---",ctx);
                    ctx.channel().attr(REASON_ATTRIBUTE_KEY).set(Reason.SessionIdle);
                    ctx.close();
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.warn("连接{}异常:{} \n 关闭连接",ctx,cause);
        ctx.channel().attr(REASON_ATTRIBUTE_KEY).set(Reason.UnKnown);
        ctx.close();
    }

}
