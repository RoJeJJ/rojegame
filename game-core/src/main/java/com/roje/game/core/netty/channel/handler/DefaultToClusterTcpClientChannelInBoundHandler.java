package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.UserManager;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.util.ServerUtil;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultToClusterTcpClientChannelInBoundHandler extends DefaultInBoundHandler {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultToClusterTcpClientChannelInBoundHandler.class);
    private BaseInfo baseInfo;
    private UserManager userManager;

    public DefaultToClusterTcpClientChannelInBoundHandler( MessageDispatcher dispatcher,
                                                           UserManager userManager,
                                                           BaseInfo baseInfo) {
        super(false,null,dispatcher);
        this.userManager = userManager;
        this.baseInfo = baseInfo;
    }



    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        LOG.info("注册到集群服务器");
        ctx.writeAndFlush(registerRequest());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.warn("连接掉了");
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt){
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    LOG.warn("读取集群服务器返回的消息超时,可能集群服务器挂掉了,关闭连接");
                    ctx.close();
                    break;
                case ALL_IDLE:
                    //如果注册成功,定时向集群服务器更新
                    if (baseInfo.getId() != 0)
                        ctx.writeAndFlush(updateRequest());
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        LOG.warn("异常",cause);
        ctx.close();
    }
    private CommonMessage.ServerRegisterRequest registerRequest(){
        CommonMessage.ServerRegisterRequest.Builder builder = CommonMessage.ServerRegisterRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder= CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType().getType());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(userManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(userManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState().getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
    private CommonMessage.ServerUpdateRequest updateRequest(){
        CommonMessage.ServerUpdateRequest.Builder builder = CommonMessage.ServerUpdateRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder= CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(baseInfo.getName());
        serverInfoBuilder.setId(baseInfo.getId());
        serverInfoBuilder.setIp(baseInfo.getIp());
        serverInfoBuilder.setType(baseInfo.getType().getType());
        serverInfoBuilder.setUserPort(baseInfo.getUserPort());
        serverInfoBuilder.setInnerPort(baseInfo.getInnerPort());
        serverInfoBuilder.setHttpport(baseInfo.getHttpPort());
        serverInfoBuilder.setMaxUserCount(baseInfo.getMaxUserCount());
        serverInfoBuilder.setOnline(userManager.getOnlineCount());
        serverInfoBuilder.setConnectedCount(userManager.getConnectedCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setTotalMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(baseInfo.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(baseInfo.getClientVersionCode());
        serverInfoBuilder.setState(baseInfo.getState().getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
}
