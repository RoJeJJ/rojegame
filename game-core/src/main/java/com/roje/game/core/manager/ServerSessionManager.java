package com.roje.game.core.manager;

import com.roje.game.core.server.ServerInfo;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;


public class ServerSessionManager {
    private static final AttributeKey<ServerInfo> SERVER_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("netty-server-info");

    private static final AttributeKey<Long> CONNECT_DATE_ATTRIBUTE_KEY = AttributeKey.newInstance("netty-connect-data");

    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void channelActive(Channel channel){
        channel.attr(CONNECT_DATE_ATTRIBUTE_KEY).set(System.currentTimeMillis());
        channels.add(channel);
    }

    public void channelInactive(Channel channel){

    }
}
