package com.roje.game.cluster.controller;

import com.roje.game.cluster.manager.ServerSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/user")
public class UserController {

    private final ServerSessionManager serverManager;

    @Autowired
    public UserController(ServerSessionManager serverManager) {
        this.serverManager = serverManager;
    }


    @PostMapping(value = "/game/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<String> allocate(
            @PathVariable("id") int gameId,
            @RequestParam("version") int version,
            @RequestParam("account") String account) {
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        serverManager.accountExecutorService(account)
                .execute(() -> {
                    String result = serverManager.allocateServer(account,gameId,version);
                    deferredResult.setResult(result);
                });
        return deferredResult;
    }


}
