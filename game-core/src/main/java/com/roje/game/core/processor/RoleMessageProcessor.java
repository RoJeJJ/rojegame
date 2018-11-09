package com.roje.game.core.processor;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;


public abstract class RoleMessageProcessor<R extends Role> extends AbsMessageProcessor{

    protected final ISessionManager sessionManager;

    protected RoleMessageProcessor(MessageDispatcher dispatcher,
                                   ISessionManager sessionManager) {
        super(dispatcher);
        this.sessionManager = sessionManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        R role = sessionManager.getRole(channel);
        handlerRoleMessage(role,frame);
    }

    protected abstract void handlerRoleMessage(R role,Frame frame) throws Exception;
}
