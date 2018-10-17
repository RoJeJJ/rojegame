package com.roje.game.gate.processor.req.game;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServerInfo;
import com.roje.game.message.server_update.ServerUpdateRequest;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Processor(action = Action.ServerUpdateReq)
public class ServerUpdateProcessor extends MessageProcessor {
    private ServerManager serverManager;

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
