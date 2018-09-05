package com.roje.game.core.netty.channel.codec;

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;
import com.roje.game.core.config.MessageConfig;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class DefaultMessageCodec extends ByteToMessageCodec<Message> {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultMessageCodec.class);
    private Predicate<ChannelHandlerContext> channelHandlerContextPredicate;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, ByteBuf byteBuf) {
        Descriptors.EnumValueDescriptor enumDescriptor = (Descriptors.EnumValueDescriptor) message.getField(message.getDescriptorForType().findFieldByNumber(1));
        int mid = enumDescriptor.getNumber();
        byte[] contentBytes = message.toByteArray();
        int contentLen = contentBytes.length + MessageConfig.MidLen;
        byteBuf.writeInt(contentLen);
        byteBuf.writeInt(mid);
        byteBuf.writeBytes(contentBytes);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
        if (byteBuf.readableBytes() < MessageConfig.headLen){
            return;
        }
        byteBuf.markReaderIndex();
        int len = byteBuf.readInt();
        if (len < 0 || len > MessageConfig.maxSize){
            LOG.warn("消息解析异常,长度:{},MAX_READ_SIZE:{}",len,MessageConfig.maxSize);
            byteBuf.clear();
            channelHandlerContext.close();
            return;
        }
        if (channelHandlerContextPredicate != null && channelHandlerContextPredicate.test(channelHandlerContext)){
            LOG.warn("消息解析异常");
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
