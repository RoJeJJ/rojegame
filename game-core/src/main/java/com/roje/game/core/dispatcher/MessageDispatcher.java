package com.roje.game.core.dispatcher;

import com.roje.game.core.processor.HttpProcessor;
import com.roje.game.core.processor.HttpRequestProcessor;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.message.action.Action;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {
    private Map<Action, MessageProcessor> handlerMap = new HashMap<>();
    private Map<String, HttpRequestProcessor> httpHandlerMap = new HashMap<>();

    public void register(MessageProcessor handler) {
            Processor processor = handler.getClass().getAnnotation(Processor.class);
            if (processor != null) {
                Action action = processor.action();
                handlerMap.put(action, handler);
            }
//        else if (handler instanceof HttpRequestProcessor) {
//            HttpRequestProcessor hrp = (HttpRequestProcessor) handler;
//            HttpProcessor httpProcessor = hrp.getClass().getAnnotation(HttpProcessor.class);
//            if (httpProcessor != null) {
//                httpHandlerMap.put(httpProcessor.path(), hrp);
//            }
//        }
    }

    public void register(HttpRequestProcessor handler){
        HttpProcessor httpProcessor = handler.getClass().getAnnotation(HttpProcessor.class);
        if (httpProcessor != null)
            httpHandlerMap.put(httpProcessor.path(),handler);
    }

    public MessageProcessor getMessageHandler(Action action) {
        return handlerMap.get(action);
    }

    @SuppressWarnings("unchecked")
    public <T extends HttpRequestProcessor> T getHttpMessageHandler(String path) {
        return (T) httpHandlerMap.get(path);
    }
}
