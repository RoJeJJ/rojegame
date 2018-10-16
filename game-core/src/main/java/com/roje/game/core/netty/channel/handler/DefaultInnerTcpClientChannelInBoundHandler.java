package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.core.util.ServerUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServerInfo;
import com.roje.game.message.server_register.ServerRegRequest;
import com.roje.game.message.server_update.ServerUpdateRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultInnerTcpClientChannelInBoundHandler extends DefaultInBoundHandler {
    private BaseInfo baseInfo;
    private ISessionManager ISessionManager;

    public DefaultInnerTcpClientChannelInBoundHandler(
            Service service,
            MessageDispatcher dispatcher,
            ISessionManager ISessionManager,
            BaseInfo baseInfo) {
        super(service, dispatcher);
        this.ISessionManager = ISessionManager;
        this.baseInfo = baseInfo;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("注册到服务器");
        MessageUtil.send(ctx.channel(), Action.ServerRegReq,registerRequest());
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
                    //如果注册成功,定时向集群服务器更新
                    if (baseInfo.getId() != 0) {
                        MessageUtil.send(ctx.channel(),Action.ServerUpdateReq,updateRequest());
                    } else
                        ctx.close();
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.warn("异常", cause);
        ctx.close();
    }

    private ServerRegRequest registerRequest() {
        ServerRegRequest.Builder builder = ServerRegRequest.newBuilder();
        ServerInfo.Builder serverInfoBuilder = ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType());
        serverInfoBuilder.setGameId(baseInfo.getGameId());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(ISessionManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(ISessionManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }

    private ServerUpdateRequest updateRequest() {
        ServerUpdateRequest.Builder builder = ServerUpdateRequest.newBuilder();
        ServerInfo.Builder serverInfoBuilder = ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(ISessionManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(ISessionManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
}
