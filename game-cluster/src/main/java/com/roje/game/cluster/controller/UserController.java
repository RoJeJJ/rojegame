package com.roje.game.cluster.controller;

import com.google.gson.JsonObject;
import com.roje.game.cluster.manager.ServerSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final ServerSessionManager serverManager;

    @Autowired
    public UserController(ServerSessionManager serverManager) {
        this.serverManager = serverManager;
    }

    @PostMapping(value = "/gameinfo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String gameInfo(
            @RequestParam("account") String account,
            @RequestParam("gameId") int gameId,
            @RequestParam("version") int version,
            @RequestParam("token") String token) {
        JsonObject response = serverManager.allocateServer(account,gameId, version, token);
        return response.toString();
    }
}
