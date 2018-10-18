package com.roje.game.lobby.controller;

import com.google.gson.JsonObject;
import com.roje.game.core.thread.executor.Executor;
import com.roje.game.lobby.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;



@Slf4j
@RestController
public class LoginController {

    private final UserService userService;

    private final Executor<String> userExecutor;

    @Autowired
    public LoginController(UserService userService,
                           Executor<String> userExecutor) {
        this.userService = userService;
        this.userExecutor = userExecutor;
    }

    @PostMapping(value = "/login/wechat",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<String> loginWx(@RequestParam("openid")String openid,
                                  @RequestParam("token")String token){
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        userExecutor.allocateThread(openid).execute(() -> {
            String result = userService.loginWechat(openid,token);
            deferredResult.setResult(result);
        });
        return deferredResult;
    }
}
