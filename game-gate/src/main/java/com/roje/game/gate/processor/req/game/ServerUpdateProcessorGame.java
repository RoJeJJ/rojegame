package com.roje.game.gate.processor.req.game;

import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@TcpProcessor(action = Action.ServerUpdateReq)
public class ServerUpdateProcessorGame extends RoleMessageProcessor {
    private ServerManager serverManager;

    public ServerUpdateProcessorGame() {
        super(dispatcher, sessionManager);
    }

    @Autowired
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServerUpdateRequest request = frame.getData().unpack(ServerUpdateRequest.class);
        ServerInfo info = request.getServerInfo();
        serverManager.updateServer(channel,info);
    }
}
