package com.roje.game.cluster.controller;

import com.google.gson.JsonObject;
import com.roje.game.cluster.manager.ServerSessionManager;
import com.roje.game.core.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class UserController {

    private final ServerSessionManager serverManager;

    @Autowired
    public UserController(ServerSessionManager serverManager) {
        this.serverManager = serverManager;
    }

    @PostMapping(value = "/gameinfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<String> gameInfo(
            @RequestParam("account") String account,
            @RequestParam("gameId") int gameId,
            @RequestParam("version") int version,
            @RequestParam("token") String token) {
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        serverManager.getService().getCustomExecutor("account").allocateThread(account)
                .execute(() -> {
                    JsonObject response = serverManager.allocateServer(account,gameId, version, token);
                    deferredResult.setResult(response.toString());
                });
        return deferredResult;
    }
}
