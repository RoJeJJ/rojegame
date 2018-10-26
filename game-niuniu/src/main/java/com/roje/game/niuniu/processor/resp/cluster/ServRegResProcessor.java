package com.roje.game.niuniu.processor.resp.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.server_info.ServRegResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.ServRegRes)
public class ServRegResProcessor extends MessageProcessor {

    private final ServerInfo serverInfo;

    public ServRegResProcessor(ServerInfo serverInfo) {
        this.serverInfo = serverInfo;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        ServRegResponse response = frame.getData().unpack(ServRegResponse.class);
        if (response.getSuccess()){
            serverInfo.setId(response.getId());
            log.info("注册成功,id:{}",response.getId());
        }else
            log.info("注册失败:{}",response.getMsg());
    }
}
