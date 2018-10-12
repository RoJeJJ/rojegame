package com.roje.game.hall.processor.resp;

import com.roje.game.core.manager.ConnGateTcpMultiManager;
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

import java.util.List;

@Slf4j
@Component
@Processor(mid = MID.ServerRegisterRes_VALUE)
public class ServerRegRespProcessor extends MessageProcessor {
    private final BaseInfo hallInfo;

    private final ConnGateTcpMultiManager connGateTcpMultiManager;

    @Autowired
    public ServerRegRespProcessor(BaseInfo hallInfo,ConnGateTcpMultiManager connGateTcpMultiManager) {
        this.hallInfo = hallInfo;
        this.connGateTcpMultiManager = connGateTcpMultiManager;
    }



    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterResponse response = CommonMessage.ServerRegisterResponse.parseFrom(bytes);
        int id = response.getServerId();
        if (response.getSuccess()){
            if (response.getRegType() == ServerType.Cluster.getType()){
                if (response.getIsMe()){
                    log.info("注册到集群成功,id:{}",id);

                    hallInfo.setId(id);

                    List<CommonMessage.ConnInfo> connInfos = response.getConnInfoList();
                    if (connInfos != null && connInfos.size() > 0){
                        for (CommonMessage.ConnInfo connInfo : connInfos)
                            connGateTcpMultiManager.connect(connInfo);
                    }else
                        log.warn("没有可用的网关服务器");
                } else if (response.getType() == ServerType.Gate.getType()){
                    CommonMessage.ConnInfo.Builder builder = CommonMessage.ConnInfo.newBuilder();
                    builder.setId(id);
                    builder.setIp(response.getIp());
                    builder.setPort(response.getPort());
                    builder.setType(response.getType());
                    connGateTcpMultiManager.connect(builder.build());
                }
            }else if (response.getRegType() == ServerType.Gate.getType()){
                if (response.getIsMe())
                    log.info("注册到网关成功");
            }
        }else
            log.warn("注册失败!");
    }
}
