package com.roje.game.cluster.netty.channel.handler;

import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class DefaultInnerTcpServerChannelInBoundHandler extends DefaultInBoundHandler {
    private ServerSessionManager serverManager;

    public DefaultInnerTcpServerChannelInBoundHandler(MessageDispatcher dispatcher,
                                                      ServerSessionManager serverManager,
                                                      Service service) {
        super(service,dispatcher);
        this.serverManager = serverManager;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        serverManager.channelActive(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx){
        serverManager.channelInactive(ctx.channel());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    log.warn("{} reader timeout --- close it");
                    ctx.close();
                    break;
            }
        }
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        super.exceptionCaught(ctx, cause);
        log.warn("连接异常,关闭连接",cause);
        ctx.close();
    }
}
