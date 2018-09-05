package com.roje.game.core.dispatcher;

import com.roje.game.core.processor.HttpProcessor;
import com.roje.game.core.processor.HttpRequestProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.processor.MessageProcessor;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {
    private Map<Integer,MessageProcessor> handlerMap = new HashMap<>();
    private Map<String,HttpRequestProcessor> httpHandlerMap = new HashMap<>();
    public void register(Object handler){
        if (handler instanceof MessageProcessor){
            MessageProcessor mp = (MessageProcessor) handler;
            Processor h = mp.getClass().getAnnotation(Processor.class);
            if (h != null)
                handlerMap.put(h.mid(),mp);
        }else if (handler instanceof HttpRequestProcessor){
            HttpRequestProcessor hrp = (HttpRequestProcessor) handler;
            HttpProcessor hp = hrp.getClass().getAnnotation(HttpProcessor.class);
            if (hp != null)
                httpHandlerMap.put(hp.path(),hrp);
        }
    }
    @SuppressWarnings("unchecked")
    public <T extends MessageProcessor>T getMessageHandler(int mid){
        return (T) handlerMap.get(mid);
    }
    @SuppressWarnings("unchecked")
    public <T extends HttpRequestProcessor> T getHttpMessageHandler(String path){
        return (T) httpHandlerMap.get(path);
    }
}
