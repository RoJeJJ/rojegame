package com.roje.game.gate.processor.impl.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Processor(mid = MID.ServerRegisterRes_VALUE)
public class ServerRegRespProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ServerRegRespProcessor.class);
    private final BaseInfo gateInfo;

    @Autowired
    public ServerRegRespProcessor(BaseInfo gateInfo) {
        this.gateInfo = gateInfo;
    }


    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterResponse response = CommonMessage.ServerRegisterResponse.parseFrom(bytes);
        int id = response.getServerId();
        if (id > 0) {
            LOG.info("注册成功,id:{}",id);
            gateInfo.setId(id);
        }
    }
}
