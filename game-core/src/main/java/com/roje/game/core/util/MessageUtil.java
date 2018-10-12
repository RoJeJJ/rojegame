package com.roje.game.core.util;

import com.roje.game.message.common.CommonMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class MessageUtil {
    /**
     * 小端字节转int
     * @param bytes 字节数组
     * @param offset 偏移
     * @param len 长度
     * @return int数据
     */
    public static int getInt(byte[] bytes, int offset, int len){
        ByteBuf buf = Unpooled.buffer(4);
        try {
            buf.writeBytes(bytes,offset,len);
            return buf.readInt();
        }finally {
            buf.release();
        }
    }

    public static long getLong(byte[] bytes,int offset,int len){
        ByteBuf buf = Unpooled.buffer(8);
        try {
            buf.writeBytes(bytes,offset,len);
            return buf.readLong();
        }finally {
            buf.release();
        }
    }

    public static byte[] getMessage(byte[] bytes,int offset,int len){
        byte[] des = new byte[len];
        System.arraycopy(bytes,offset,des,0,len);
        return des;
    }

    public static CommonMessage.ErrorResponse errorResponse(CommonMessage.SystemErrCode code, String msg) {
        CommonMessage.ErrorResponse.Builder builder = CommonMessage.ErrorResponse.newBuilder();
        builder.setErrorCode(code);
        if (org.springframework.util.StringUtils.isEmpty(msg))
            msg = "UnKnown";
        builder.setMsg(msg);
        return builder.build();
    }

    public static void send(Channel channel, int mid,long uid, byte[] content){
        if (channel != null && channel.isActive()) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(mid);
            buf.writeLong(uid);
            buf.writeBytes(content);
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            channel.writeAndFlush(bytes);
        }else
            log.warn("session消息发送失败");
    }

    public static void send(Channel channel, int mid, byte[] content){
        if (channel != null && channel.isActive()) {
            ByteBuf buf = Unpooled.buffer();
            buf.writeInt(mid);
            buf.writeBytes(content);
            byte[] bytes = new byte[buf.readableBytes()];
            buf.readBytes(bytes);
            channel.writeAndFlush(bytes);
        }else
            log.warn("session消息发送失败");
    }
}
