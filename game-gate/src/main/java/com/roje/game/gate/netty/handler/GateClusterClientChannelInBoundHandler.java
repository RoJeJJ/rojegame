package com.roje.game.gate.netty.handler;

import com.roje.game.core.config.ServerConfig;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.server.ServerState;
import com.roje.game.core.service.ClientService;
import com.roje.game.core.util.ServerUtil;
import com.roje.game.gate.mannager.UserSessionManager;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class GateClusterClientChannelInBoundHandler extends DefaultInBoundHandler {
    private static final Logger LOG = LoggerFactory.getLogger(GateClusterClientChannelInBoundHandler.class);
    private ServerConfig serverConfig;
    private UserSessionManager sessionManager;
    private ClientService clientService;

    public void setClientService(ClientService clientService) {
        this.clientService = clientService;
    }

    public void setServerConfig(ServerConfig serverConfig) {
        this.serverConfig = serverConfig;
    }

    public void setSessionManager(UserSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LOG.info("注册到集群服务器");
        ctx.writeAndFlush(registerRequest());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        LOG.warn("连接掉了");
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    LOG.warn("读取集群服务器返回的消息超时,可能集群服务器挂掉了,关闭连接");
                    ctx.close();
                    break;
                case ALL_IDLE:
                    ctx.writeAndFlush(updateRequest());
                    break;

            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn("异常",cause);
        ctx.close();
    }
    private CommonMessage.ServerRegisterRequest registerRequest(){
        CommonMessage.ServerRegisterRequest.Builder builder = CommonMessage.ServerRegisterRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder= CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(serverConfig.getName());
        serverInfoBuilder.setId(serverConfig.getId());
        serverInfoBuilder.setIp(serverConfig.getIp());
        serverInfoBuilder.setType(serverConfig.getType().getType());
        serverInfoBuilder.setPort(serverConfig.getPort());
        serverInfoBuilder.setHttpport(serverConfig.getHttpPort());
        serverInfoBuilder.setMaxUserCount(serverConfig.getMaxUserCount());
        serverInfoBuilder.setOnline(sessionManager.getOnlineCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setFreeMemory(ServerUtil.totalMemory());
        serverInfoBuilder.setVersion(serverConfig.getVersionCode());
        serverInfoBuilder.setRequireClientVersion(serverConfig.getClientVersionCode());
        serverInfoBuilder.setState(ServerState.NORMAL.getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
    private CommonMessage.ServerUpdateRequest updateRequest(){
        CommonMessage.ServerUpdateRequest.Builder builder = CommonMessage.ServerUpdateRequest.newBuilder();
        CommonMessage.ServerInfo.Builder serverInfoBuilder= CommonMessage.ServerInfo.newBuilder();
        serverInfoBuilder.setName(serverConfig.getName());
        serverInfoBuilder.setId(serverConfig.getId());
        serverInfoBuilder.setIp(serverConfig.getIp());
        serverInfoBuilder.setType(serverConfig.getType().getType());
        serverInfoBuilder.setPort(serverConfig.getPort());
        serverInfoBuilder.setHttpport(serverConfig.getHttpPort());
        serverInfoBuilder.setMaxUserCount(serverConfig.getMaxUserCount());
        serverInfoBuilder.setOnline(sessionManager.getOnlineCount());
        serverInfoBuilder.setFreeMemory(ServerUtil.freeMemory());
        serverInfoBuilder.setVersion(serverConfig.getVersionCode());
        serverInfoBuilder.setState(ServerState.NORMAL.getState());
        builder.setServerInfo(serverInfoBuilder);
        return builder.build();
    }
}
