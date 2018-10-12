package com.roje.game.gate.processor.resp.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(mid = MID.ServerRegisterRes_VALUE)
public class ServerRegRespProcessor extends MessageProcessor {
    private final BaseInfo gateInfo;

    @Autowired
    public ServerRegRespProcessor(BaseInfo gateInfo) {
        this.gateInfo = gateInfo;
    }


    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterResponse response = CommonMessage.ServerRegisterResponse.parseFrom(bytes);
        if (response.getSuccess()) {
            if (response.getIsMe()) {
                if (response.getRegType() == ServerType.Cluster.getType()) {
                    log.info("注册到集群服务器成功,id:{}", response.getServerId());
                    gateInfo.setId(response.getServerId());
                } else if (response.getRegType() == ServerType.Gate.getType())
                    log.info("注册到网关服务器成功");
            }
        }else
            log.warn("注册失败");
    }
}
