package com.roje.game.cluster.processor;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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
        log.info("接受{}:{}/{}的注册请求",info.getName(),info.getIp(),info.getPort());
        if (StringUtils.isEmpty(info.getIp()) || info.getPort() < 0 || info.getPort() > 65535 ){
            log.info("无法注册{}/{}",info.getIp(),info.getPort());
            return;
        }
        ServerType type = ServerType.valueOf(info.getType());
        if (type == ServerType.UnKnown)
            log.warn("服务器类型未知");
        else
            log.info("服务器类型{}",type.name());
        int id = serverManager.registerServerWithoutID(info,channel);
        if (id == 0)
            log.info("服务器注册失败");
        else {
            log.info("注册成功");
            CommonMessage.ServerRegisterResponse.Builder builder = CommonMessage.ServerRegisterResponse.newBuilder();
            builder.setServerId(id);
            channel.writeAndFlush(builder.build());
        }
    }
}
