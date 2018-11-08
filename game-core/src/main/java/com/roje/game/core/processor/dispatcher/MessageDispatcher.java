package com.roje.game.core.processor.dispatcher;

import com.roje.game.core.processor.*;
import com.roje.game.message.action.Action;

import java.util.HashMap;
import java.util.Map;

public class MessageDispatcher {
    private Map<Action, MessageProcessor> handlerMap = new HashMap<>();
    private Map<String, MessageProcessor> httpHandlerMap = new HashMap<>();

    public void register(MessageProcessor handler) {
        TcpProcessor tcpProcessor = handler.getClass().getAnnotation(TcpProcessor.class);
        if (tcpProcessor != null) {
            Action action = tcpProcessor.action();
            handlerMap.put(action, handler);
        }
        HttpProcessor httpProcessor = handler.getClass().getAnnotation(HttpProcessor.class);
        if (httpProcessor != null)
            httpHandlerMap.put(httpProcessor.path(), handler);

    }

    public MessageProcessor getTcpMessageHandler(Action action) {
        return handlerMap.get(action);
    }


    public MessageProcessor getHttpMessageHandler(String path) {
        return  httpHandlerMap.get(path);
    }
}
