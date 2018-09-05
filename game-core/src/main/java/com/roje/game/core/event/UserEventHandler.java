package com.roje.game.core.event;

import com.roje.game.model.constant.Reason;
import io.netty.channel.ChannelHandlerContext;

public interface UserEventHandler {
    default void quit(ChannelHandlerContext cx,Reason reason){}
}
