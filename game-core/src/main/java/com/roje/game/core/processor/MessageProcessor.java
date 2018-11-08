package com.roje.game.core.processor;

import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;

public interface MessageProcessor {
    void handler(Channel channel, Frame frame)throws Exception;
}
