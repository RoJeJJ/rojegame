package com.roje.game.cluster.processor.req.server;

import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.processor.AbsMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServInfoRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.ServInfoReq,thread = ThreadType.def)
public class ServerInfoReqProcessor extends AbsMessageProcessor {

    private final ServerSessionManager serverManager;

    @Autowired
    public ServerInfoReqProcessor(MessageDispatcher dispatcher,
                                  ServerSessionManager serverManager) {
        super(dispatcher);
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServInfoRequest info = frame.getData().unpack(ServInfoRequest.class);
        serverManager.serverInfo(channel,info.getServInfo());
    }
}
