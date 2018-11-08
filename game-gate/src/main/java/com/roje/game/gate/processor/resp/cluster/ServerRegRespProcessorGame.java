package com.roje.game.gate.processor.resp.cluster;

import com.roje.game.core.processor.RoleMessageProcessor;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_register.ServerRegResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.ServerRegRes)
public class ServerRegRespProcessorGame extends RoleMessageProcessor {
    private final BaseInfo gateInfo;

    @Autowired
    public ServerRegRespProcessorGame(BaseInfo gateInfo) {
        super(dispatcher, sessionManager);
        this.gateInfo = gateInfo;
    }


    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServerRegResponse response = frame.getData().unpack(ServerRegResponse.class);
        if (response.getSuccess()) {
            switch (response.getType()){
                case Cluster:
                    int id = response.getId();
                    log.info("注册到集群服务器成功,id:{}", id);
                    gateInfo.setId(id);
                    break;

            }
        }else
            log.warn("注册失败:{}",response.getMsg());
    }
}
