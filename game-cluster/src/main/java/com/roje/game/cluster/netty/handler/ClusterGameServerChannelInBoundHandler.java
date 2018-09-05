package com.roje.game.cluster.netty.handler;

import com.roje.game.cluster.manager.ServerManager;
import com.roje.game.cluster.server.ClusterTcpTcpServer;
import com.roje.game.core.netty.channel.handler.DefaultInBoundHandler;
import com.roje.game.core.server.ServerInfo;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ChannelHandler.Sharable
public class ClusterGameServerChannelInBoundHandler extends DefaultInBoundHandler {
    private static final Logger LOG = LoggerFactory.getLogger(ClusterGameServerChannelInBoundHandler.class);
    private ServerManager serverManager;

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ServerInfo serverInfo = ctx.channel().attr(ClusterTcpTcpServer.SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfo != null) {
            LOG.warn("服务器:{}断开连接", serverInfo.getName());
            serverManager.unRegister(serverInfo,ctx);
        }else
            LOG.warn("未知连接断开");
        super.channelInactive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            switch (event.state()){
                case READER_IDLE:
                    LOG.warn("{} reader timeout --- close it");
                    ctx.close();
                    break;
                case WRITER_IDLE:
                    LOG.warn("{} writer timeout");
                    break;
                case ALL_IDLE:
                    LOG.warn("{} all timeout");
                    break;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        LOG.warn("{}连接异常,关闭连接");
        ctx.close();
    }
}
