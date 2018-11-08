package com.roje.game.core.util;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.ErrorMessage;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.inner_message.InnerMessage;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


@Slf4j
public class MessageUtil {
    public static <T extends Message> void send(Channel channel, Action action, T message){
        if (channel != null && channel.isActive()){
            Frame.Builder builder = Frame.newBuilder();
            builder.setAction(action);
            builder.setData(Any.pack(message));
            channel.writeAndFlush(builder.build());
        }else
            log.info("channelInActive,消息发送失败");
    }

    public static <T extends Message> void send(ChannelGroup channels, Action action, T message){
        Frame.Builder builder = Frame.newBuilder();
        builder.setAction(action);
        builder.setData(Any.pack(message));
        channels.writeAndFlush(builder.build());
    }

    public static void sendErrorData(Channel channel, ErrorData errorData){
        if (channel != null && channel.isActive()){
            ErrorMessage.Builder builder = ErrorMessage.newBuilder();
            builder.setCode(errorData.getCode());
            builder.setMsg(errorData.getMsg());

            Frame.Builder fb = Frame.newBuilder();
            fb.setAction(Action.ErrorRes);
            fb.setData(Any.pack(builder.build()));
            channel.writeAndFlush(fb.build());
        }
    }



    public static void send(Channel channel, long uid, Frame frame){
        if (channel != null && channel.isActive()) {
            Frame.Builder frameBuilder = frame.toBuilder();
            InnerMessage.Builder builder = InnerMessage.newBuilder();
            builder.setUid(uid);
            builder.setData(frameBuilder.getData());
            frameBuilder.setData(Any.pack(builder.build()));
            channel.writeAndFlush(frameBuilder.build());
        }else
            log.warn("session消息发送失败");
    }

    public static void send(Channel channel,Frame frame){
        if (channel != null && channel.isActive())
            channel.writeAndFlush(frame);
        else
            log.warn("session消息发送失败");
    }
}
