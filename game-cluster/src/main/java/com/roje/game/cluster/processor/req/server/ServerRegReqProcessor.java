package com.roje.game.cluster.processor.req.server;

import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.processor.AbsMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServInfo;
import com.roje.game.message.server_info.ServRegRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.ServRegReq_VALUE,thread = ThreadType.def)
public class ServerRegReqProcessor extends AbsMessageProcessor {
    private final ServerSessionManager serverManager;

    @Autowired
    public ServerRegReqProcessor(MessageDispatcher dispatcher,
                                 ServerSessionManager serverManager) {
        super(dispatcher);
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServRegRequest request = frame.getData().unpack(ServRegRequest.class);
        ServInfo servInfo = request.getServInfo();
        serverManager.register(channel,servInfo);
    }
}
