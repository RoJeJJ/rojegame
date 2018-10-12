package com.roje.game.core.netty.channel.handler;

import com.google.protobuf.Message;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.core.util.ServerUtil;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultInnerTcpClientChannelInBoundHandler extends DefaultInBoundHandler {
    private BaseInfo baseInfo;
    private SessionManager sessionManager;
    private boolean hasID;

    public DefaultInnerTcpClientChannelInBoundHandler(
            boolean hasID,
            Service service,
            MessageDispatcher dispatcher,
            SessionManager sessionManager,
            BaseInfo baseInfo) {
        super(hasID, service, dispatcher);
        this.sessionManager = sessionManager;
        this.baseInfo = baseInfo;
        this.hasID = hasID;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        log.info("注册到服务器");
        CommonMessage.ServerRegisterRequest registerRequest = registerRequest();
        send(ctx.channel(), registerRequest.getMid().getNumber(), registerRequest);
    }

    private void send(Channel channel, int mid, Message message) {
        if (hasID)
            MessageUtil.send(channel, mid, 0, message.toByteArray());
        else
            MessageUtil.send(channel, mid, message.toByteArray());
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
                        CommonMessage.ServerUpdateRequest updateRequest = updateRequest();
                        send(ctx.channel(), updateRequest.getMid().getNumber(), updateRequest);
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

    private CommonMessage.ServerRegisterRequest registerRequest() {
        CommonMessage.ServerRegisterRequest.Builder builder = CommonMessage.ServerRegisterRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder = CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType().getType());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(sessionManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(sessionManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState().getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }

    private CommonMessage.ServerUpdateRequest updateRequest() {
        CommonMessage.ServerUpdateRequest.Builder builder = CommonMessage.ServerUpdateRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder = CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType().getType());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(sessionManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(sessionManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState().getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
}
