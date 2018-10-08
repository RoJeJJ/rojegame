package com.roje.game.core.netty.channel.handler;

import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.processor.HttpProcessor;
import com.roje.game.core.processor.HttpRequestProcessor;
import com.roje.game.core.service.Service;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

import static com.roje.game.core.util.HttpUtils.sendError;

@ChannelHandler.Sharable
public class DefaultHttpInBoundHandler extends SimpleChannelInboundHandler<DefaultHttpRequest> {
    private static Logger LOG = LoggerFactory.getLogger(DefaultHttpInBoundHandler.class);
    private MessageDispatcher dispatcher;
    private Service service;

    public void setDispatcher(MessageDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    public void setService(Service service) {
        this.service = service;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DefaultHttpRequest request) throws Exception {
        //400
        //405
        LOG.info("接收请求:"+request.uri());
        if (request.method() != HttpMethod.GET){
            System.out.println("bad method");
            sendError(channelHandlerContext,HttpResponseStatus.METHOD_NOT_ALLOWED);
            return;
        }
        String path = request.uri();
        path = path.substring(0,path.indexOf("?"));
        LOG.info("path:{}",path);
        HttpRequestProcessor handler = dispatcher.getHttpMessageHandler(path);
        //404
        if (handler == null){
            sendError(channelHandlerContext,HttpResponseStatus.NOT_FOUND);
            return;
        }
        HttpProcessor httpProcessor = handler.getClass().getAnnotation(HttpProcessor.class);
        if (httpProcessor == null){
            sendError(channelHandlerContext,HttpResponseStatus.NOT_FOUND);
            return;
        }
        Executor executor = service.getExecutor(httpProcessor.thread());
        if (executor != null){
            executor.execute(() -> handler.handler(channelHandlerContext,request));
        }else
            handler.handler(channelHandlerContext,request);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOG.warn("cause an exception",cause);
    }
}
