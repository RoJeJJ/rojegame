package com.roje.game.core.processor;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;


public abstract class RoleMessageProcessor extends AbsMessageProcessor{

    protected final ISessionManager sessionManager;

    protected RoleMessageProcessor(MessageDispatcher dispatcher,
                                   ISessionManager sessionManager) {
        super(dispatcher);
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        Role role = sessionManager.getRole(channel);
        handlerRoleMessage(role,frame);
    }

    protected abstract <R extends Role> void handlerRoleMessage(R role,Frame frame) throws Exception;
}
