package com.roje.game.core.manager;

import com.roje.game.core.session.Session;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class UserSessionManager<T extends Session> implements UserManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserSessionManager.class);

    private final AttributeKey<T> SESSION_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.session");
    /**
     * 所有连接的channel ,key为channelId
     */
    private final Map<String, T> allSessions = new ConcurrentHashMap<>();
    /**
     * 未登录用户,key为channelId
     */
    private final Map<String, T> anonymousSessions = new ConcurrentHashMap<>();
    /**
     * 已登录用户,key为uid
     */
    private final Map<Long, T> onlineSessions = new ConcurrentHashMap<>();


    public void sessionOpen(T session){
        allSessions.put(session.id(),session);
        anonymousSessions.put(session.id(),session);
        session.channel().attr(SESSION_ATTRIBUTE_KEY).set(session);
        LOG.info("session:{} open", session.id());
    }

    public void sessionClosed(Channel channel) {
        Session session = channel.attr(SESSION_ATTRIBUTE_KEY).get();
        if (session != null){
            allSessions.remove(session.id());
            anonymousSessions.remove(session.id());
            onlineSessions.remove(session.getUid());
            session.channel().attr(SESSION_ATTRIBUTE_KEY).set(null);
            session.sessionClosed();
            LOG.info("session:{} closed",session.id());
        }
    }

    public void onShutDown() {
        // TODO: 2018/9/15
    }

    @Override
    public int getOnlineCount() {
        return onlineSessions.size();
    }

    @Override
    public int getConnectedCount() {
        return allSessions.size();
    }

    public T getSession(Channel channel){
        return channel.attr(SESSION_ATTRIBUTE_KEY).get();
    }

    public T getAnonymousSession(String sessionId){
        return anonymousSessions.get(sessionId);
    }

    public T getLoggedSession(long uid){
        return onlineSessions.get(uid);
    }

    public void sessionLogin(T session){
        anonymousSessions.remove(session.id());
        onlineSessions.put(session.getUid(),session);
    }
}
