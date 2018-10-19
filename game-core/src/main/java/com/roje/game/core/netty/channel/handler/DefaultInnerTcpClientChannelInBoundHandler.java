package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServInfoRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultInnerTcpClientChannelInBoundHandler extends DefaultInBoundHandler {
    private ServerInfo serverInfo;
    private ISessionManager sessionManager;

    public DefaultInnerTcpClientChannelInBoundHandler(
            MessageDispatcher dispatcher,
            ISessionManager sessionManager,
            ServerInfo serverInfo) {
        super(null, dispatcher);
        this.sessionManager = sessionManager;
        this.serverInfo = serverInfo;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("向集群发送消息");
        MessageUtil.send(ctx.channel(), Action.ServInfoReq,servInfo());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()) {
                case READER_IDLE:
                    log.warn("读取服务器返回的消息超时,可能服务器挂掉了,关闭连接");
                    ctx.close();
                    break;
                case ALL_IDLE:
                    MessageUtil.send(ctx.channel(),Action.ServInfoReq,servInfo());
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("异常", cause);
        ctx.close();
    }

    private ServInfoRequest servInfo(){
        ServInfoRequest.Builder builder = ServInfoRequest.newBuilder();
        builder.setIp(serverInfo.getIp());
        builder.setPort(serverInfo.getPort());
        builder.setGameId(serverInfo.getGameId());
        builder.setName(serverInfo.getName());
        builder.setOnline(sessionManager.getOnlineCount());
        builder.setMaxUserCount(serverInfo.getMaxUserCount());
        builder.setRequireVersion(serverInfo.getRequireVersion());
        return builder.build();
    }
}
