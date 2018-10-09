package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.config.MessageConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;


@Slf4j
public class DefaultInBoundHandler extends SimpleChannelInboundHandler<byte[]> {
    private MessageDispatcher dispatcher;
    protected Service service;
    private boolean containUid;

    public DefaultInBoundHandler(boolean containUid,Service service,MessageDispatcher dispatcher){
        this.containUid = containUid;
        this.service = service;
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, byte[] bytes) throws Exception {
        if (bytes.length < MessageConfig.MidLen){
            log.error("消息长度{}小于消息号长度{}",bytes.length,MessageConfig.MidLen);
            channelHandlerContext.close();
            return;
        }
        int mid = MessageUtil.getInt(bytes,0,MessageConfig.MidLen);
        long uid = containUid?MessageUtil.getLong(bytes,MessageConfig.MidLen,MessageConfig.UidLen):0;
        MessageProcessor handler = dispatcher.getMessageHandler(mid);
        byte[] msg = MessageUtil.getMessage(bytes,MessageConfig.MidLen+(containUid?MessageConfig.UidLen:0),bytes.length - MessageConfig.MidLen);
        boolean forward = true;
        if (handler != null){
            Processor processor = handler.getClass().getAnnotation(Processor.class);
            if (processor != null){
                if (service == null){ //如果没有执行服务,默认当前线程执行任务
                    handler.handler(channelHandlerContext.channel(),msg);
                }else {
                    Executor executor = service.getExecutor(processor.thread());
                    if (executor != null){
                        executor.execute(() -> {
                            try {
                                handler.handler(channelHandlerContext.channel(),msg);
                            } catch (Exception e) {
                                log.warn("处理消息异常",e);
                            }
                        });
                    }else //没有找到执行线程或线程池,当前线程执行
                        handler.handler(channelHandlerContext.channel(),msg);
                }
                forward = processor.forward();
            }
        }
        if (forward)
            forward(channelHandlerContext,mid,uid,msg);
    }
    public void forward(ChannelHandlerContext ctx,int mid,long uid,byte[] bytes){}
}
