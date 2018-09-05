package com.roje.game.gate.processor.impl.cluster;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.Mid;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Processor(mid = Mid.MID.ServerUpdateRes_VALUE)
public class ServerUpdateProcessor extends MessageProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(ServerUpdateProcessor.class);
    @Override
    public void handler(ChannelHandlerContext ctx, byte[] bytes) {
//        LOG.info("向集群服务器更新信息成功");
    }
}
