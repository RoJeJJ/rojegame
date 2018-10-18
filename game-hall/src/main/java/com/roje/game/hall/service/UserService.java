package com.roje.game.hall.service;

import com.google.gson.JsonObject;
import com.roje.game.core.entity.User;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.lobby.entity.WxUser;
import com.roje.game.hall.utils.WeChatUtil;
import com.roje.game.message.login.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Slf4j
@Service
public class UserService {

    private final UserRedisService userRedisService;

    private final IdService idService;

    @Autowired
    public UserService(UserRedisService userRedisService,
                       IdService idService) {
        this.userRedisService = userRedisService;
        this.idService = idService;
    }


    public LoginResponse loginWeChat(String openId, String token) {
        JsonObject data = WeChatUtil.userInfo(openId, token);
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        if (data == null || data.has("errcode")) {
            log.warn("微信验证失败");
            builder.setSuccess(false);
            builder.setMsg("微信验证失败");
        } else {


        }
        return builder.build();
    }

    public LoginResponse loginAccount(String username, String password) {
        User user = userRedisService.get(username);
        return null;
    }
}
