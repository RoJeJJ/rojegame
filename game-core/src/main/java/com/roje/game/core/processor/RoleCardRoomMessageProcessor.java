package com.roje.game.core.processor;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.entity.role.impl.CardRole;
import com.roje.game.core.manager.room.impl.CardRoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.message.frame.Frame;

public abstract class RoleCardRoomMessageProcessor extends RoleMessageProcessor {
    protected final CardRoomManager roomManager;
    protected RoleCardRoomMessageProcessor(MessageDispatcher dispatcher,
                                           ISessionManager sessionManager,
                                           CardRoomManager roomManager) {
        super(dispatcher, sessionManager);
        this.roomManager = roomManager;
    }

    @Override
    public <R extends Role> void handlerRoleMessage(R role, Frame frame) throws Exception{
        CardRole r = (CardRole) role;
        handlerRoleMessage(r,frame);
    }

    public abstract <R extends CardRole> void handlerRoleMessage(R role,Frame frame) throws Exception;
}
