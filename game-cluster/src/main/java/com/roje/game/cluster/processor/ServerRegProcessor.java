package com.roje.game.cluster.processor;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_register.ServerRegRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Processor(action = Action.ServerRegReq)
public class ServerRegProcessor extends MessageProcessor {
    private ServerManager serverManager;

    @Autowired
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServerRegRequest regRequest = frame.getData().unpack(ServerRegRequest.class);
        serverManager.registerServer(regRequest.getServerInfo(),channel);
    }
}
