package com.roje.game.gate.processor.impl.user;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.util.TimeUtil;
import com.roje.game.message.Mid.MID;
import com.roje.game.message.common.CommonMessage.HeartBeatResponse;
import io.netty.channel.ChannelHandlerContext;

@Processor(mid = MID.HeartReq_VALUE)
public class HeartBeatProcessor extends MessageProcessor {
    @Override
    public void handler(ChannelHandlerContext ctx, byte[] bytes) {
        HeartBeatResponse.Builder builder = HeartBeatResponse.newBuilder();
        builder.setServerTime(TimeUtil.currentTimeMillis());
        ctx.writeAndFlush(builder.build());
    }
}
