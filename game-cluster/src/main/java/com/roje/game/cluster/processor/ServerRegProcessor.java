package com.roje.game.cluster.processor;

import com.roje.game.core.manager.ServerManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import static com.roje.game.message.Mid.MID.ServerRegisterReq_VALUE;

@Processor(mid = ServerRegisterReq_VALUE)
public class ServerRegProcessor extends MessageProcessor {
    private ServerManager serverManager;
    private static final Logger LOG = LoggerFactory.getLogger(ServerRegProcessor.class);

    public void setServerManager(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
        CommonMessage.ServerRegisterRequest request = CommonMessage.ServerRegisterRequest.parseFrom(bytes);
        CommonMessage.ServerInfo info = request.getServerInfo();
        LOG.info("接受{}:{}/{}的注册请求",info.getName(),info.getIp(),info.getPort());
        if (StringUtils.isEmpty(info.getIp()) || info.getPort() < 0 || info.getPort() > 65535 ){
            LOG.info("无法注册{}/{}",info.getIp(),info.getPort());
            return;
        }
        ServerType type = ServerType.valueOf(info.getType());
        if (type == ServerType.UnKnown)
            LOG.warn("服务器类型未知");
        else
            LOG.info("服务器类型{}",type.name());
        int id = serverManager.registerServerWithoutID(info,channel);
        if (id == 0)
            LOG.info("服务器注册失败");
        else {
            LOG.info("注册成功");
            CommonMessage.ServerRegisterResponse.Builder builder = CommonMessage.ServerRegisterResponse.newBuilder();
            builder.setServerId(id);
            channel.writeAndFlush(builder.build());
        }
    }
}
