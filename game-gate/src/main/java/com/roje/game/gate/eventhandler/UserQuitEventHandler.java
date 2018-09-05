package com.roje.game.gate.eventhandler;

import com.roje.game.core.event.UserEventHandler;
import com.roje.game.gate.mannager.UserSessionManager;
import com.roje.game.gate.session.UserSession;
import com.roje.game.model.constant.Reason;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UserQuitEventHandler implements UserEventHandler {
    private static final Logger LOG = LoggerFactory.getLogger(UserQuitEventHandler.class);
    @Autowired
    private UserSessionManager sessionManager;
    @Override
    public void quit(ChannelHandlerContext ctx, Reason reason) {
        Attribute<UserSession> attr = ctx.channel().attr(AttributeKey.valueOf("user.session"));
        if (attr == null || attr.get() == null){
            LOG.warn("session is null");
            return;
        }
        UserSession session = attr.get();
    }
}
