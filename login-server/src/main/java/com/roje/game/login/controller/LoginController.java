package com.roje.game.login.controller;

import com.roje.game.core.service.Service;
import com.roje.game.login.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.ScheduledExecutorService;


@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class LoginController {

    private final UserService userService;

    private final Service service;
    @Autowired
    public LoginController(UserService userService,
                           Service service) {
        this.userService = userService;
        this.service = service;
    }

    private ScheduledExecutorService getAccountExecutor(String account){
        return service.getCustomExecutor("account").allocateThread(account);
    }

    @PostMapping(value = "/login/wechat")
    public DeferredResult<String> loginWx(@RequestParam("openid")String openid,
                                  @RequestParam("token")String token){
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        getAccountExecutor(openid).execute(() -> {
            String result = userService.loginWechat(openid,token);
            deferredResult.setResult(result);
        });
        return deferredResult;
    }

    @PostMapping(value = "login/account")
    public DeferredResult<String> loginAcc(@RequestParam("account") String account,
                                           @RequestParam("password") String password){
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        getAccountExecutor(account).execute(() -> {
            String result = userService.loginAcc(account,password);
            deferredResult.setResult(result);
        });
        return deferredResult;
    }

    @PostMapping(value = "/register")
    public DeferredResult<String> register(@RequestParam("account") String account,
                                           @RequestParam("password") String password){
        final DeferredResult<String> deferredResult = new DeferredResult<>();
        getAccountExecutor(account).execute(() -> {
            String result = userService.register(account,password);
            deferredResult.setResult(result);
        });
        return deferredResult;
    }
}
