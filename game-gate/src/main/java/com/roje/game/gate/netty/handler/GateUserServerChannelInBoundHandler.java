package com.roje.game.gate.netty.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.service.Service;
import com.roje.game.gate.manager.GateSessionSessionManager;
import com.roje.game.gate.session.GateUserSession;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GateUserServerChannelInBoundHandler extends DefaultInBoundHandler {
    private GateSessionSessionManager sessionManager;

    public GateUserServerChannelInBoundHandler(Service service, MessageDispatcher dispatcher, GateSessionSessionManager sessionManager) {
        super(false,service,dispatcher);
        this.sessionManager = sessionManager;
    }


    @Override
    public void forward(ChannelHandlerContext ctx,int mid,long uid, byte[] bytes) {
        GateUserSession session = sessionManager.getSession(ctx.channel());
        if (session == null) {
            log.warn("未知请求,关闭连接");
            ctx.close();
            return;
        }
        if (mid > 20000) { //消息号大于20000的消息转发到游戏服
           session.sendToGame(true,mid,bytes);
        } else if (mid > 10000) { //大于10000,小于20000 转发到大厅服务器
            session.sendToHall(true,mid,bytes);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        GateUserSession session = new GateUserSession(ctx.channel());
        sessionManager.sessionOpen(session);
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        sessionManager.sessionClosed(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    log.warn("{} reader timeout,close it---", ctx);
                    ctx.close();
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("连接{}异常:{} \n 关闭连接", ctx, cause);
        ctx.close();
    }

}
