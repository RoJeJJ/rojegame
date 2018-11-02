package com.roje.game.login.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.roje.game.core.entity.User;
import com.roje.game.core.redis.lock.LoginLock;
import com.roje.game.core.redis.service.IdService;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.login.utils.WeChatUtil;
import com.roje.game.login.entity.WxUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class UserService {

    private final UserRedisService userRedisService;

    private final IdService idService;

    private final LoginLock loginLock;

    @Autowired
    public UserService(UserRedisService userRedisService,
                       IdService idService, LoginLock loginLock) {
        this.userRedisService = userRedisService;
        this.idService = idService;
        this.loginLock = loginLock;
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
        RLock lock = loginLock.getLock(openid);
        lock.lock(10, TimeUnit.SECONDS);
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
            userRedisService.save(user);
        }
        String gameToken = userRedisService.getToken(openid);
        if (gameToken == null)
            gameToken = userRedisService.generateToken(openid);
        lock.unlock();
        log.info("game-token:{}",gameToken);
        response.addProperty("success",true);
        response.add("info",user.userInfo());
        response.addProperty("game-token",gameToken);
        return response.toString();
    }

    public String loginAcc(String account, String password) {
        JsonObject response = new JsonObject();
        RLock lock = loginLock.getLock(account);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            User user = userRedisService.get(account);
            log.info("{}",user);
            if (user == null || !StringUtils.equals(password,user.getPassword())){
                response.addProperty("success",false);
                response.addProperty("msg","用户名或密码错误");
                return response.toString();
            }
            String gameToken = userRedisService.getToken(account);
            if (gameToken == null)
                gameToken = userRedisService.generateToken(account);
            log.info("game-token:{}",gameToken);
            response.addProperty("success",true);
            response.add("info",user.userInfo());
            response.addProperty("game-token",gameToken);
            return response.toString();
        }finally {
            lock.unlock();
        }
    }

    public String register(String account,String password){
        JsonObject response = new JsonObject();
        RLock lock = loginLock.getLock(account);
        lock.lock(10, TimeUnit.SECONDS);
        try {
            User user = userRedisService.get(account);
            if (user != null){
                response.addProperty("success",false);
                response.addProperty("msg","用户名已经存在");
                return response.toString();
            }
            user = new User();
            user.setAccount(account);
            user.setPassword(password);
            user.setHeadimg(null);
            user.setSex(1);
            user.setId(idService.generate());
            user.setGold(10000);
            user.setNickname(null);
            userRedisService.save(user);

            response.addProperty("success",true);
            response.addProperty("msg","注册成功");
            return response.toString();
        }finally {
            lock.unlock();
        }
    }
}
