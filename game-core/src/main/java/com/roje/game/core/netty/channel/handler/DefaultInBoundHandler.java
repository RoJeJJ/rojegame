package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.service.Service;
import com.roje.game.message.frame.Frame;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;


@Slf4j
public class DefaultInBoundHandler extends SimpleChannelInboundHandler<Frame> {
    private MessageDispatcher dispatcher;
    protected Service service;

    public DefaultInBoundHandler(@Nullable Service service, @NotNull MessageDispatcher dispatcher) {
        this.service = service;
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame frame) throws Exception {
        MessageProcessor handler = dispatcher.getMessageHandler(frame.getAction());
        boolean forward = true;
        if (handler != null) {
            Processor processor = handler.getClass().getAnnotation(Processor.class);
            if (processor != null) {
                if (service == null) { //如果没有执行任务的服务,默认当前线程执行任务
                    handler.handler(channelHandlerContext.channel(), frame);
                } else {
                    Executor executor = service.getExecutor(processor.thread());
                    if (executor != null) {
                        executor.execute(() -> {
                            try {
                                handler.handler(channelHandlerContext.channel(), frame);
                            } catch (Exception e) {
                                log.warn("处理消息异常", e);
                            }
                        });
                    } else {//没有找到执行线程或线程池,当前线程执行
                        handler.handler(channelHandlerContext.channel(), frame);
                    }
                }
                forward = processor.forward();
            }
        }
        if (forward)
            forward(channelHandlerContext, frame);
    }

    public void forward(ChannelHandlerContext ctx, Frame frame) {}
}
