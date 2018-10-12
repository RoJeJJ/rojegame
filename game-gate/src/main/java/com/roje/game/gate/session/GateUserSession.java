package com.roje.game.gate.session;

import com.google.protobuf.Message;
import com.roje.game.core.config.MessageConfig;
import com.roje.game.core.session.Session;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.common.CommonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.logging.Handler;

@Slf4j
public class GateUserSession extends Session {
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
//        LoginMessage.LoseConnection.Builder builder = LoginMessage.LoseConnection.newBuilder();
//        builder.setUid(uid);
//        builder.setRid(rid);
//        if (hallServer != null){
//            hallServer.send(builder.build());
//        }
//        if (gameServer != null){
//            gameServer.send(builder.build());
//        }
    }

    public void sendToHall(boolean auth,int mid, byte[] content){
        if (hallServer != null){
            sendTo(auth,hallServer.getChannel(),mid,content);
        }else {
                log.warn("没有可用的大厅服务器");
                channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErrCode.HallNotFind, "没有可用的大厅服务器"));
        }
    }

    private void sendTo(boolean auth,Channel channel,int mid,byte[] content){
        if (auth && uid == 0){
            log.warn("还没有登录");
            send(MessageUtil.errorResponse(CommonMessage.SystemErrCode.NotLoginOn,"请先登录"));
        }else
            MessageUtil.send(channel,mid,uid,content);
    }

    public void sendToGame(boolean auth,int mid, byte[] content){
        if (gameServer != null){
            sendTo(auth,gameServer.getChannel(),mid,content);
        }else {
            log.warn("没有可用的游戏服务器");
            channel.writeAndFlush(MessageUtil.errorResponse(CommonMessage.SystemErrCode.HallNotFind, "没有可用的大厅服务器"));
        }
    }
}
