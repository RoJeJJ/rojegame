package com.roje.game.lobby.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.roje.game.core.entity.User;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.lobby.utils.WeChatUtil;
import com.roje.game.lobby.entity.WxUser;
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

    public String loginWechat(String openid,String token){
        JsonObject data = WeChatUtil.userInfo(openid,token);
        JsonObject response = new JsonObject();
        if (data == null || data.has("errcode")){
            log.warn("微信验证失败");
            response.addProperty("success",false);
            response.addProperty("msg","微信验证失败");
            return response.toString();
        }
        WxUser wxUser = new Gson().fromJson(data,WxUser.class);
        User user = userRedisService.get(openid);
        if (user == null){
            user = new User();
            user.setAccount(openid);
            user.setPassword(token);
            user.setHeadimg(wxUser.getHeadimgurl());
            user.setSex(wxUser.getSex());
            user.setId(idService.generate());
            user.setGold(10000);
            user.setNickname(wxUser.getNickname());
            user.setLogged(false);
            userRedisService.save(user);
        }
        if (user.isLogged()){
            //已经登录
            // TODO: 2018/10/18  踢下线
        }
        user.setLogged(true);
        response.addProperty("success",true);
        response.add("info",user.userInfo());
        String gameToken = userRedisService.generateToken(user);
        log.info("game-token:{}",gameToken);
        response.addProperty("game-token",gameToken);
        return response.toString();
    }
}
