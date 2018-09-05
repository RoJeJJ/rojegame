package com.roje.game.gate.mannager;

import com.roje.game.gate.session.UserSession;
import com.roje.game.model.constant.Reason;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserSessionManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserSessionManager.class);
    private Map<String,UserSession> allSessions = new ConcurrentHashMap<>();

    /**
     * 用户连接到服务器
     * @param session session
     */
    public void userConnected(UserSession session){
        if (session.clientChannel() != null)
            allSessions.put(session.clientChannel().channel().id().asLongText(),session);
    }

    public void remove(UserSession session, Reason reason){
        if (session.clientChannel() != null){
            allSessions.remove(session.clientChannel().channel().id().asLongText());
        }
    }

    public void onShutDown(){
        for (UserSession session:allSessions.values()){
            // TODO: 2018/8/22 通知其他服务器
            remove(session,Reason.ServerClose);
        }
    }
    public UserSession getUserSessionById(ChannelHandlerContext ctx){
        return allSessions.get(ctx.channel().id().asLongText());
    }

    public int getOnlineCount(){
        return allSessions.size();
    }
}
