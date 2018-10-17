package com.roje.game.gate.manager;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.manager.SessionManager;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.gate.session.GateUserSession;
import com.roje.game.message.error.ErrorCode;
import com.roje.game.message.server_info.ServerInfo;
import com.roje.game.message.server_info.ServerType;
import io.netty.channel.Channel;


public class GateSessionManager extends SessionManager<GateUserSession> {

    private ServerManager serverManager;

    public GateSessionManager(ServerManager serverManager){
        this.serverManager = serverManager;
    }

    @Override
    public void sessionLogin(GateUserSession session) {
    }

    @Override
    public void onSessionOpen(GateUserSession session) {
        ServerInfo serverInfo = serverManager.getIdleServer(ServerType.Hall,session.getVersionCode());
        if (serverInfo == null){
            MessageUtil.sendError(session.channel(), ErrorCode.HallNotFind,"大厅服务器不可用");
            session.close();
            return;
        }
        Channel channel = serverManager.getChannel(serverInfo.getId());
        session.setHallChannel(channel);
    }

    @Override
    public void onSessionClosed(GateUserSession session) {

    }

    @Override
    public int getOnlineCount() {
        return 0;
    }
}
