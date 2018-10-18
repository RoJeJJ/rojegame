package com.roje.game.hall.manager;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.roje.game.core.entity.User;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.lobby.entity.WxUser;
import com.roje.game.hall.utils.WeChatUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.login.InnerLoginResponse;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import com.roje.game.message.user_info.UserInfo;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HallUserManager implements ISessionManager {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    private final IdService idService;

    public HallUserManager(UserRedisService userRedisService, IdService idService) {
        this.userRedisService = userRedisService;
        this.idService = idService;
    }

    @Override
    public int getOnlineCount() {
        return userMap.size();
    }

    @Override
    public int getConnectedCount() {
        return 0;
    }

    public void login(Channel channel, LoginRequest request, String sessionId) {
        User user = userMap.get(request.getAccount());
        LoginResponse response;
        if (user != null) {
            log.warn("{}已经登录了", user);
            LoginResponse.Builder builder = LoginResponse.newBuilder();
            builder.setSuccess(false);
            builder.setMsg("已经登录了");
            response = builder.build();
        } else {
            LoginResponse.Builder builder = LoginResponse.newBuilder();
            user = userRedisService.get(request.getAccount());
            switch (request.getLoginType()) {
                case WeChat:
                    JsonObject data = WeChatUtil.userInfo(request.getAccount(), request.getPassword());
                    if (data == null || data.has("errcode")) {
                        log.warn("微信验证失败");
                        builder.setSuccess(false);
                        builder.setMsg("微信验证失败");
                    } else {
                        WxUser wxUser = new Gson().fromJson(data, WxUser.class);
                        if (user == null) {
                            user = new User();
                            user.setAccount(request.getAccount());
                            user.setPassword(request.getPassword());
                            user.setHeadimg(wxUser.getHeadimgurl());
                            user.setSex(wxUser.getSex());
                            user.setId(idService.genrate());
                            user.setGold(10000);
                            user.setNickname(wxUser.getNickname());
                            userRedisService.save(user);
                        }
                    }
                    break;
                case Account:
                    if (user == null) {
                        builder.setSuccess(false);
                        builder.setMsg("账号或密码错误");
                        break;
                    }
                    if (!StringUtils.equals(user.getPassword(), request.getPassword())) {
                        builder.setSuccess(false);
                        builder.setMsg("账号或密码错误");
                        break;
                    }

                    break;
                default:
                    builder.setSuccess(false);
                    builder.setMsg("登录方式错误");
                    break;
            }
            if (builder.getSuccess() && user != null){
                builder.setSuccess(true);
                builder.setMsg("登录成功");
                UserInfo.Builder userInfoBuilder = UserInfo.newBuilder();
                userInfoBuilder.setAccount(user.getAccount())
                        .setId(user.getId())
                        .setHeadimg(user.getHeadimg())
                        .setNickname(user.getNickname())
                        .setGold(user.getGold());
                builder.setUserInfo(userInfoBuilder.build());
            }
            response = builder.build();
        }
        if (response.getSuccess()) {
            userMap.put(request.getAccount(), user);
        }
        InnerLoginResponse.Builder b = InnerLoginResponse.newBuilder();
        b.setSessionId(sessionId);
        b.setResponse(response);
        MessageUtil.send(channel, Action.LoginRes, b.build());
    }
}
