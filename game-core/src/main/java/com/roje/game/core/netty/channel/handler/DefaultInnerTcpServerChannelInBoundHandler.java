package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DefaultInnerTcpServerChannelInBoundHandler extends DefaultInBoundHandler {
    private ServerManager serverManager;

    public DefaultInnerTcpServerChannelInBoundHandler(boolean hasID,Service service, MessageDispatcher dispatcher, ServerManager serverManager) {
        super(hasID,service,dispatcher);
        this.serverManager = serverManager;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        serverManager.channelInactive(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    log.warn("{} reader timeout --- close it");
                    ctx.close();
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
//        super.exceptionCaught(ctx, cause);
        log.warn("连接异常,关闭连接",cause);
        ctx.close();
    }
}
