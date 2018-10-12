package com.roje.game.gate.processor.req.game;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerType;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

import static com.roje.game.message.Mid.MID.ServerRegisterReq_VALUE;

@Slf4j
@Component
@Processor(mid = ServerRegisterReq_VALUE)
public class ServerRegReqProcessor extends MessageProcessor {
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
        CommonMessage.ServerRegisterResponse.Builder builder = CommonMessage.ServerRegisterResponse.newBuilder();
        builder.setRegType(ServerType.Gate.getType());
        builder.setIsMe(true);
        if (serverInfo == null) {
            builder.setSuccess(false);
            log.info("{}注册到网关服务器失败", ((InetSocketAddress) channel.remoteAddress()).getHostName());
        }else {
            builder.setSuccess(true);
            log.info("{}注册到网关服务器成功",info.getName());
        }
        MessageUtil.send(channel,builder.getMid().getNumber(),0,builder.build().toByteArray());
    }
}
