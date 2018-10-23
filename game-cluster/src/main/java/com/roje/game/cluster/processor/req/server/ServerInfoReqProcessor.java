package com.roje.game.cluster.processor.req.server;

import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.service.Service;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServInfoRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ScheduledExecutorService;

@Slf4j
@Component
@Processor(action = Action.ServInfoReq,thread = ThreadType.def)
public class ServerInfoReqProcessor extends MessageProcessor {

    private final ServerSessionManager serverManager;

    private final Service service;

    @Autowired
    public ServerInfoReqProcessor(ServerSessionManager serverManager, Service service) {
        this.serverManager = serverManager;
        this.service = service;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServInfoRequest info = frame.getData().unpack(ServInfoRequest.class);
        serverManager.serverInfo(channel,info.getServInfo());
    }
}
