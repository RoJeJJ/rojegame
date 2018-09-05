package com.roje.game.gate.processor.impl.cluster;

import com.roje.game.core.config.ServerConfig;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Processor(mid = MID.ServerRegisterRes_VALUE)
public class ServerRegProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ServerRegProcessor.class);
    private ServerConfig gateUserServerConfig;
    private ServerConfig gateGameServerConfig;

    public void setGateGameServerConfig(ServerConfig gateGameServerConfig) {
        this.gateGameServerConfig = gateGameServerConfig;
    }

    public void setGateUserServerConfig(ServerConfig gateUserServerConfig) {
        this.gateUserServerConfig = gateUserServerConfig;
    }

    @Override
    public void handler(ChannelHandlerContext ctx, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterResponse response = CommonMessage.ServerRegisterResponse.parseFrom(bytes);
        int id = response.getServerId();
        LOG.info("注册成功,id:{}",id);
        if (id > 0) {
            gateGameServerConfig.setId(id);
            gateUserServerConfig.setId(id);
        }
    }
}
