package com.roje.game.niuniu.processor.resp.cluster;

import com.roje.game.core.processor.AbsMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.thread.ThreadType;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@TcpProcessor(action = Action.ServInfoRes,thread = ThreadType.def)
public class ServInfoResProcessor extends AbsMessageProcessor {
    public ServInfoResProcessor(MessageDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
//        ServInfoResponse response = frame.getData().unpack(ServInfoResponse.class);
//        log.info(response.getMsg());
    }
}
