package com.roje.game.cluster.controller;

import com.google.gson.JsonObject;
import com.roje.game.cluster.manager.ServerSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final ServerSessionManager serverManager;

    @Autowired
    public UserController(ServerSessionManager serverManager) {
        this.serverManager = serverManager;
    }

    @GetMapping(value = "/gameinfo",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String gameInfo(@RequestParam("gameId")int gameId,
                         @RequestParam("version")int version){
        JsonObject response = serverManager.allocateServer(gameId,version);
        return response.toString();
    }
}
