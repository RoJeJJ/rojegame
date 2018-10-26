package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServInfo;
import com.roje.game.message.server_info.ServInfoRequest;
import com.roje.game.message.server_info.ServRegRequest;
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
        MessageUtil.send(ctx.channel(), Action.ServRegReq,serverReg());
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
                    if (serverInfo.getId() != 0)
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

    private ServRegRequest serverReg(){
        ServRegRequest.Builder builder = ServRegRequest.newBuilder();
        ServInfo.Builder infoBuilder = ServInfo.newBuilder();
        infoBuilder.setId(serverInfo.getId());
        infoBuilder.setIp(serverInfo.getIp());
        infoBuilder.setPort(serverInfo.getPort());
        infoBuilder.setGameId(serverInfo.getGameId());
        infoBuilder.setName(serverInfo.getName());
        infoBuilder.setOnline(sessionManager.getOnlineCount());
        infoBuilder.setMaxUserCount(serverInfo.getMaxUserCount());
        infoBuilder.setRequireVersion(serverInfo.getRequireVersion());
        builder.setServInfo(infoBuilder);
        return builder.build();
    }

    private ServInfoRequest servInfo(){
        ServInfoRequest.Builder builder = ServInfoRequest.newBuilder();
        ServInfo.Builder infoBuilder = ServInfo.newBuilder();
        infoBuilder.setId(serverInfo.getId());
        infoBuilder.setIp(serverInfo.getIp());
        infoBuilder.setPort(serverInfo.getPort());
        infoBuilder.setGameId(serverInfo.getGameId());
        infoBuilder.setName(serverInfo.getName());
        infoBuilder.setOnline(sessionManager.getOnlineCount());
        infoBuilder.setMaxUserCount(serverInfo.getMaxUserCount());
        infoBuilder.setRequireVersion(serverInfo.getRequireVersion());
        builder.setServInfo(infoBuilder);
        return builder.build();
    }
}
