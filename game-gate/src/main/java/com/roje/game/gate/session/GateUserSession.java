package com.roje.game.gate.session;

import com.roje.game.core.config.MessageConfig;
import com.roje.game.core.session.Session;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.common.CommonMessage;
import com.roje.game.message.login.LoginMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GateUserSession extends Session {
    private static final Logger LOG = LoggerFactory.getLogger(GateUserSession.class);
    private ServerInfo hallServer;
    private ServerInfo gameServer;
    public GateUserSession(Channel channel) {
        super(channel);
    }

    public ServerInfo getHallServer() {
        return hallServer;
    }

    public void setHallServer(ServerInfo hallServer) {
        this.hallServer = hallServer;
    }

    public ServerInfo getGameServer() {
        return gameServer;
    }

    public void setGameServer(ServerInfo gameServer) {
        this.gameServer = gameServer;
    }

    @Override
    public void sessionClosed() {
        LoginMessage.LoseConnection.Builder builder = LoginMessage.LoseConnection.newBuilder();
        builder.setUid(uid);
        builder.setRid(rid);
        if (hallServer != null){
            hallServer.send(builder.build());
        }
        if (gameServer != null){
            gameServer.send(builder.build());
        }
    }

    public void sendToHall(int mid, byte[] content){
        if (hallServer != null){
            send(hallServer.getChannel(),mid,content);
        }else {
                LOG.warn("没有可用的大厅服务器");
                channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErroCode.HallNotFind, "没有可用的大厅服务器"));
        }
    }

    public void sendToGame(int mid, byte[] content){
        if (gameServer != null){
            send(gameServer.getChannel(),mid,content);
        }else {
            LOG.warn("没有可用的游戏服务器");
            channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErroCode.HallNotFind, "没有可用的大厅服务器"));
        }
    }

    private void send(Channel channel,int mid,byte[] content){
        if (channel != null && channel.isActive()) {
            int len = MessageConfig.MidLen + MessageConfig.UidLen + content.length;
            ByteBuf buf = Unpooled.buffer(len);
            buf.writeInt(mid);
            buf.writeLong(uid);
            buf.writeBytes(content);
            channel.writeAndFlush(buf);
        }else
            LOG.warn("session消息发送失败");
    }
}
