package com.roje.game.core.manager;

import com.roje.game.core.session.Session;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.error.ErrorCode;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class SessionManager<T extends Session> implements ISessionManager {

    private final AttributeKey<T> SESSION_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.session");
    /**
     * 所有连接的channel ,key为channelId
     */
    private final Map<String, T> sessions = new ConcurrentHashMap<>();



    public void sessionOpen(T session){
        sessions.put(session.id(),session);
        session.channel().attr(SESSION_ATTRIBUTE_KEY).set(session);
        log.info("session:{} open", session.id());
        session.open();
        onSessionOpen(session);
    }

    public void sessionClosed(Channel channel) {
        T session = channel.attr(SESSION_ATTRIBUTE_KEY).get();
        if (session != null){
            sessions.remove(session.id());
            session.channel().attr(SESSION_ATTRIBUTE_KEY).set(null);
            session.onClosed();
            onSessionClosed(session);
            log.info("session:{} closed",session.id());
        }
    }

    @Override
    public int getConnectedCount() {
        return sessions.size();
    }

    public void onShutDown() {
        // TODO: 2018/9/15
    }

    public T getSession(Channel channel){
        T session = channel.attr(SESSION_ATTRIBUTE_KEY).get();
        if (session == null) {
            log.warn("连接会话已失效");
            MessageUtil.sendError(channel, ErrorCode.ConnectReset,"连接失效,请重连");
            channel.close();
        }
        return session;
    }

    public T getSessionById(Channel channel,String sid){
        T session = sessions.get(sid);
        if (session == null) {
            log.warn("连接会话已失效");
            MessageUtil.sendError(channel, ErrorCode.ConnectReset,"连接失效,请重连");
            channel.close();
        }
        return session;
    }

    public abstract void sessionLogin(T session);

    public void onSessionOpen(T session){}

    public void onSessionClosed(T session){}
}
