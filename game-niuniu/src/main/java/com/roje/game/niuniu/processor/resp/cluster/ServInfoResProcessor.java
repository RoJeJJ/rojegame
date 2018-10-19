package com.roje.game.niuniu.processor.resp.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(action = Action.ServInfoRes)
public class ServInfoResProcessor extends MessageProcessor {
    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
//        ServInfoResponse response = frame.getData().unpack(ServInfoResponse.class);
//        log.info(response.getMsg());
    }
}
