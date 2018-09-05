package com.roje.game.cluster.processor;

import com.roje.game.cluster.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.Mid;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.ChannelHandlerContext;

@Processor(mid = Mid.MID.ServerUpdateReq_VALUE)
public class ServerUpdateProcessor extends MessageProcessor {
    private ServerManager serverManager;

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        CommonMessage.ServerUpdateRequest request = CommonMessage.ServerUpdateRequest.parseFrom(bytes);
        CommonMessage.ServerInfo info = request.getServerInfo();
        ctx.writeAndFlush(serverManager.updateServer(info));
    }
}
