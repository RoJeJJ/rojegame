package com.roje.game.hall.processor.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.hall.client.HallToGateTcpClient;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Processor(mid = MID.ServerRegisterRes_VALUE)
public class ServerRegRespProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ServerRegRespProcessor.class);
    private BaseInfo hallInfo;
    private HallToGateTcpClient hallToGateTcpClient;

    public void setHallInfo(BaseInfo hallInfo) {
        this.hallInfo = hallInfo;
    }

    public void setHallToGateTcpClient(HallToGateTcpClient hallToGateTcpClient) {
        this.hallToGateTcpClient = hallToGateTcpClient;
    }

    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterResponse response = CommonMessage.ServerRegisterResponse.parseFrom(bytes);
        int id = response.getServerId();
        if (id > 0) {
            hallInfo.setId(id);
            LOG.info("注册成功,id:{}",id);
            if (hallToGateTcpClient != null)
                new Thread(hallToGateTcpClient).start();
        }
    }
}
