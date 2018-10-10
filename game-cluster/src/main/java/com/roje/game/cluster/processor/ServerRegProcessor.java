package com.roje.game.cluster.processor;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;

import static com.roje.game.message.Mid.MID.ServerRegisterReq_VALUE;

@Slf4j
@Component
@Processor(mid = ServerRegisterReq_VALUE)
public class ServerRegProcessor extends MessageProcessor {
    private ServerManager serverManager;

    @Autowired
    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterRequest request = CommonMessage.ServerRegisterRequest.parseFrom(bytes);
        CommonMessage.ServerInfo info = request.getServerInfo();
        ServerInfo serverInfo = serverManager.registerServer(info,channel);
        if (serverInfo == null)
            log.info("服务器注册失败");
        else {
            log.info("注册成功");
            CommonMessage.ServerRegisterResponse.Builder builder = CommonMessage.ServerRegisterResponse.newBuilder();
            builder.setServerId(serverInfo.getId());
            builder.setIp(serverInfo.getIp());
            builder.setPort(serverInfo.getInnerPort());
            builder.setType(info.getType());
            if (serverInfo.getType() == ServerType.Hall.getType() || serverInfo.getType() == ServerType.Game.getType()){
                List<CommonMessage.ConnInfo> connInfos = serverManager.getGateConnInfo(info.getVersion());
                builder.addAllConnInfo(connInfos);
            }
            builder.setIsMe(true);
            channel.writeAndFlush(builder.build());
            if (serverInfo.getType() == ServerType.Gate.getType()){
                builder.setIsMe(false);
                serverManager.publishGateServerConnected(builder.build(),info.getVersion());
            }
        }
    }
}
