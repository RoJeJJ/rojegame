package com.roje.game.core.manager.session;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.exception.RJException;
import io.netty.channel.Channel;

public interface ISessionManager<R extends Role> {
    int getOnlineCount();
    void sessionOpen(Channel channel);
    void sessionClose(Channel channel);
    void login(Channel channel, String account) throws RJException;
    R getRole(Channel channel);
    R getRole(String account);
}
