package com.roje.game.hall.manager;

import com.roje.game.core.entity.User;
import com.roje.game.core.manager.ISessionManager;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.hall.service.UserService;
import com.roje.game.message.action.Action;
import com.roje.game.message.login.InnerLoginResponse;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class HallUserManager implements ISessionManager {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    private final UserService userService;

    public HallUserManager(
            UserService userService){
        this.userService = userService;
    }

    @Override
    public int getOnlineCount() {
        return userMap.size();
    }

    @Override
    public int getConnectedCount() {
        return 0;
    }

    public void login(Channel channel,LoginRequest request,String sessionId){
        User user = userMap.get(request.getAccount());
        LoginResponse response;
        if (user != null) {
            log.warn("{}已经登录了",user);
            LoginResponse.Builder builder = LoginResponse.newBuilder();
            builder.setSuccess(false);
            builder.setMsg("已经登录了");
            response = builder.build();
        }else {
            switch (request.getLoginType()){
                case WeChat:
                    response = userService.loginWeChat(request.getAccount(),request.getPassword());
                    break;
                case Account:
                    response = userService.loginAccount(request.getAccount(),request.getPassword());
                    break;
                    default:
                        LoginResponse.Builder builder = LoginResponse.newBuilder();
                        builder.setSuccess(false);
                        builder.setMsg("登录方式错误");
                        response = builder.build();
                        break;
            }
        }
        if (response.getSuccess()){
            userMap.put(request.getAccount(),user);
        }
        InnerLoginResponse.Builder b = InnerLoginResponse.newBuilder();
        b.setSessionId(sessionId);
        b.setResponse(response);
        MessageUtil.send(channel,Action.LoginRes,b.build());
    }
}
