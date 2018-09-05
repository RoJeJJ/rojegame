package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.config.MessageConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

public class DefaultInBoundHandler extends SimpleChannelInboundHandler<byte[]> {
    private MessageDispatcher dispatcher;
    protected Service service;
    private static final Logger LOG = LoggerFactory.getLogger(DefaultInBoundHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        if (bytes.length < MessageConfig.MidLen){
            LOG.error("消息长度{}小于消息号长度{}",bytes.length,MessageConfig.MidLen);
            channelHandlerContext.close();
            return;
        }
        int mid = MessageUtil.getMid(bytes,0,MessageConfig.MidLen);
        MessageProcessor handler = dispatcher.getMessageHandler(mid);
        byte[] msg = MessageUtil.getMessage(bytes,MessageConfig.MidLen,bytes.length - MessageConfig.MidLen);
        if (handler != null){
            Processor annotationProcessor = handler.getClass().getAnnotation(Processor.class);
            if (annotationProcessor != null){
                Executor executor = service.getExecutor(annotationProcessor.thread());
                if (executor != null){
                    executor.execute(() -> {
                        try {
                            handler.handler(channelHandlerContext,msg);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }else
                    handler.handler(channelHandlerContext,msg);
            }
            forward(channelHandlerContext,mid,bytes);
        }
    }
    public void forward(ChannelHandlerContext ctx,int mid,byte[] bytes){}

    public void setDispatcher(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
