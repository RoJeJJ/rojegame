package com.roje.game.core.util;

import com.google.protobuf.Any;
import com.google.protobuf.Message;
import com.roje.game.message.action.Action;
import com.roje.game.message.error.ErrorCode;
import com.roje.game.message.error.ErrorMessage;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.inner_message.InnerMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageUtil {
    public static <T extends Message> void send(Channel channel, Action action, T message){
        if (channel != null && channel.isActive()){
            Frame.Builder builder = Frame.newBuilder();
            builder.setAction(action);
            builder.setData(Any.pack(message));
            channel.writeAndFlush(builder.build());
        }else
            log.warn("消息发送失败");
    }

    public static void sendError(Channel channel, ErrorCode errorCode,String msg){
        ErrorMessage.Builder errBuilder = ErrorMessage.newBuilder();
        errBuilder.setErrCode(errorCode);
        errBuilder.setErrMsg(msg);
        send(channel,Action.PubErrorMessage,errBuilder.build());
    }


    public static void sendError(Channel channel){
        sendError(channel,ErrorCode.UnknownErrType,"未知错误");
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
