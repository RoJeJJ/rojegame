package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.TcpProcessor;
import com.roje.game.core.service.Service;
import com.roje.game.message.frame.Frame;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;


@Slf4j
public class DefaultInBoundHandler extends SimpleChannelInboundHandler<Frame> {
    private MessageDispatcher dispatcher;
    @Getter
    protected Service service;

    public DefaultInBoundHandler(@Nullable Service service, @NotNull MessageDispatcher dispatcher) {
        this.service = service;
        this.dispatcher = dispatcher;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Frame frame) throws Exception {
        MessageProcessor handler = dispatcher.getTcpMessageHandler(frame.getAction());
        boolean execute = true;
        if (handler != null) {
            if (service != null) {
                TcpProcessor tcpProcessor = handler.getClass().getAnnotation(TcpProcessor.class);
                if (tcpProcessor != null) {
                    Executor executor = service.getExecutor(tcpProcessor.thread());
                    if (executor != null) {
                        execute = false;
                        executor.execute(() -> {
                            try {
                                handler.handler(channelHandlerContext.channel(), frame);
                            } catch (Exception e) {
                                log.warn("处理消息异常", e);
                            }
                        });

                    }
                }
            }
            if (execute) {
                handler.handler(channelHandlerContext.channel(), frame);
            }
        }
    }
}
