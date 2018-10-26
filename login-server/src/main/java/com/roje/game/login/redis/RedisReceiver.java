package com.roje.game.login.redis;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RedisReceiver{
    private Map<String,DeferredResult<String>> resultMap = new ConcurrentHashMap<>();

    public void receiveMessage(String message) {
        JsonObject data = new JsonParser().parse(message).getAsJsonObject();
        String uuid = data.get("uuid").getAsString();
        DeferredResult<String> deferredResult = resultMap.remove(uuid);
        if (deferredResult != null)
            deferredResult.setResult(data.remove("uuid").toString());
    }

    public void setDeferredResult(String uuid,DeferredResult<String> deferredResult){
        resultMap.put(uuid,deferredResult);
    }
}
