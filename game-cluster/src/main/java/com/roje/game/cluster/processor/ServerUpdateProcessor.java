package com.roje.game.cluster.processor;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.Mid;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Processor(mid = Mid.MID.ServerUpdateReq_VALUE)
public class ServerUpdateProcessor extends MessageProcessor {
    private ServerManager serverManager;

    @Autowired
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerUpdateRequest request = CommonMessage.ServerUpdateRequest.parseFrom(bytes);
        CommonMessage.ServerInfo info = request.getServerInfo();
        channel.writeAndFlush(serverManager.updateServer(info));
    }
}
