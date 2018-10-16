package com.roje.game.gate.processor.resp.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.ServerUpdateRes)
public class ServerUpdateRespProcessor extends MessageProcessor {
    @Override
    public void handler(Channel channel, Frame frame) {
//        log.info("向集群服务器更新信息成功");
    }
}
