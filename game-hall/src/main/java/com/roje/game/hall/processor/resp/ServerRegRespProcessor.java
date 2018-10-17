package com.roje.game.hall.processor.resp;

import com.roje.game.core.netty.NettyGateTcpClient;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.conn_info.ConnInfo;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServerType;
import com.roje.game.message.server_register.ServerRegResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@Processor(action = Action.ServerRegRes)
public class ServerRegRespProcessor extends MessageProcessor {

    private final NettyGateTcpClient nettyGateTcpClient;

    @Autowired
    public ServerRegRespProcessor(NettyGateTcpClient nettyGateTcpClient) {
        this.nettyGateTcpClient = nettyGateTcpClient;
    }



    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServerRegResponse response = frame.getData().unpack(ServerRegResponse.class);
        int id = response.getId();
        if (response.getSuccess()){
            if (response.getType() == ServerType.Cluster){
                log.info("注册到集群成功,id:{}",id);
                nettyGateTcpClient.getBaseInfo().setId(id);
                List<ConnInfo> connInfos = response.getGateInfoList();
                if (connInfos != null && connInfos.size() > 0){
                    for (ConnInfo connInfo:connInfos)
                        nettyGateTcpClient.connect(connInfo.getIp(),connInfo.getPort());
                }else
                    log.warn("没有可用的网关服务器");
            }else if (response.getType() == ServerType.Gate){
                log.info("注册到网关成功");
            }
        }else
            log.warn("注册失败!");
    }
}
