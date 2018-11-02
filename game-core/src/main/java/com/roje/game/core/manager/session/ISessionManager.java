package com.roje.game.core.manager.session;

import com.roje.game.core.entity.Role;
import com.roje.game.core.exception.RJException;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import io.netty.channel.Channel;

public interface ISessionManager<R extends Role,M> {
    int getOnlineCount();
    void sessionOpen(Channel channel);
    void sessionClose(Channel channel);
    void login(Channel channel, String account) throws RJException;
    R getRole(Channel channel);
    R getRole(String account);
    M createRoom(CreateCardRoomRequest request) throws RJException;
}
