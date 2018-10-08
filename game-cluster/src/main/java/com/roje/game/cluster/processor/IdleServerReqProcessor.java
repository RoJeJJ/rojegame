package com.roje.game.cluster.processor;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.Mid;
import io.netty.channel.Channel;

@Processor(mid = Mid.MID.IdleServerReq_VALUE)
public class IdleServerReqProcessor extends MessageProcessor {
    @Override
    public void handler(Channel channel, byte[] bytes) throws Exception {
    }
}
