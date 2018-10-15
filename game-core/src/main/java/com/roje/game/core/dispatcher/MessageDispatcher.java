package com.roje.game.core.dispatcher;

import com.roje.game.core.processor.HttpProcessor;
import com.roje.game.core.processor.HttpRequestProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.message.action.Action;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {
    private Map<Action, MessageProcessor> handlerMap = new HashMap<>();
    private Map<String, HttpRequestProcessor> httpHandlerMap = new HashMap<>();

    public void register(Object handler) {
        if (handler instanceof MessageProcessor) {
            MessageProcessor mp = (MessageProcessor) handler;
            Processor processor = mp.getClass().getAnnotation(Processor.class);
            if (processor != null) {
                Action action = processor.action();
                handlerMap.put(action, mp);
            }
        } else if (handler instanceof HttpRequestProcessor) {
            HttpRequestProcessor hrp = (HttpRequestProcessor) handler;
            HttpProcessor httpProcessor = hrp.getClass().getAnnotation(HttpProcessor.class);
            if (httpProcessor != null) {
                httpHandlerMap.put(httpProcessor.path(), hrp);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends MessageProcessor> T getMessageHandler(Action action) {
        return (T) handlerMap.get(action);
    }

    @SuppressWarnings("unchecked")
    public <T extends HttpRequestProcessor> T getHttpMessageHandler(String path) {
        return (T) httpHandlerMap.get(path);
    }
}
