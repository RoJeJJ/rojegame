package com.roje.game.core.netty.channel.codec;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.roje.game.core.config.MessageConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Predicate;

@Slf4j
public class DefaultMessageCodec extends ByteToMessageCodec<Object> {
    private Predicate<ChannelHandlerContext> channelHandlerContextPredicate;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object message, ByteBuf byteBuf) {
        if (message instanceof Message){
            Message msg = (Message) message;
            Descriptors.EnumValueDescriptor enumDescriptor = (Descriptors.EnumValueDescriptor) msg.getField(msg.getDescriptorForType().findFieldByNumber(1));
            int mid = enumDescriptor.getNumber();
            byte[] contentBytes = msg.toByteArray();
            int contentLen = contentBytes.length + MessageConfig.MidLen;
            byteBuf.writeInt(contentLen);
            byteBuf.writeInt(mid);
            byteBuf.writeBytes(contentBytes);
        }else if (message instanceof byte[]){
            byte[] content = (byte[]) message;
            byteBuf.writeInt(content.length);
            byteBuf.writeBytes(content);
        }
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if (byteBuf.readableBytes() < MessageConfig.headLen){
            return;
        }
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();
        if (len < 0 || len < MessageConfig.headLen){
            log.warn("消息解析异常,长度:{},HEAD_SIZE:{}",len,MessageConfig.headLen);
            byteBuf.clear();
            channelHandlerContext.close();
            return;
        }
        if (channelHandlerContextPredicate != null && channelHandlerContextPredicate.test(channelHandlerContext)){
            log.warn("消息解析异常");
            byteBuf.clear();
            channelHandlerContext.close();
            return;
        }
        if (byteBuf.readableBytes() < len){
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        list.add(bytes);
    }

    public void setChannelHandlerContextPredicate(Predicate<ChannelHandlerContext> channelHandlerContextPredicate) {
        this.channelHandlerContextPredicate = channelHandlerContextPredicate;
    }
}
