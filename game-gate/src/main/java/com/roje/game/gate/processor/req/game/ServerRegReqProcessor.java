package com.roje.game.gate.processor.req.game;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServerInfo;
import com.roje.game.message.server_register.ServerRegRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.ServerRegReq)
public class ServerRegReqProcessor extends MessageProcessor {
    private ServerManager serverManager;

    @Autowired
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServerRegRequest request = frame.getData().unpack(ServerRegRequest.class);
        ServerInfo info = request.getServerInfo();
        serverManager.registerServer(info,channel);
    }
}
