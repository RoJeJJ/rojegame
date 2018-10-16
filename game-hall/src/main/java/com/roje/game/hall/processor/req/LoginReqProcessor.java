package com.roje.game.hall.processor.req;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.roje.game.core.entity.User;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.service.redis.IdService;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.hall.entity.WxUser;
import com.roje.game.hall.utils.WeChatUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.InnerLoginRequest;
import com.roje.game.message.login.InnerLoginResponse;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import com.roje.game.message.user_info.UserInfo;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Processor(action = Action.LoginReq)
public class LoginReqProcessor extends MessageProcessor {

    private final UserRedisService userRedisService;

    private final IdService idService;

    @Autowired
    public LoginReqProcessor(UserRedisService userRedisService,
                             IdService idService) {
        this.userRedisService = userRedisService;
        this.idService = idService;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        InnerLoginRequest innerLoginRequest = frame.getData().unpack(InnerLoginRequest.class);
        LoginRequest loginRequest = innerLoginRequest.getRequest();
        log.info("请求登录:{},{}", loginRequest.getAccount(),loginRequest.getPassword());
        switch (loginRequest.getLoginType()){
            case WeChat:
                loginWeChat(channel,loginRequest,innerLoginRequest.getSessionId());
                break;
            case Account:
                loginAccount(channel,loginRequest,innerLoginRequest.getSessionId());
                break;
        }
    }

    private void loginWeChat(Channel channel, LoginRequest request,String sessionId){
        String openid = request.getAccount();
        String token = request.getPassword();
        JsonObject data = WeChatUtil.userInfo(openid,token);
        InnerLoginResponse.Builder innerBuilder = InnerLoginResponse.newBuilder();
        innerBuilder.setSessionId(sessionId);
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        if (data == null || data.has("errcode")){
            builder.setSuccess(false);
            builder.setMsg("微信验证失败");
            innerBuilder.setResponse(builder.build());
        }else {
            WxUser wxUser = new Gson().fromJson(data,WxUser.class);
            User user = userRedisService.get(openid);
            if (user == null){
                user = new User();
                user.setAccount(openid);
                user.setPassword(token);
                user.setHeadimg(wxUser.getHeadimgurl());
                user.setSex(wxUser.getSex());
                user.setId(idService.genrate());
                user.setGold(10000);
                userRedisService.save(user);
            }
            user.setGameToken(UUID.randomUUID().toString().replace("-",""));
            builder.setSuccess(true);
            UserInfo.Builder userInfoBuilder = UserInfo.newBuilder();
            userInfoBuilder.setAccount(user.getAccount())
                    .setId(user.getId())
                    .setHeadimg(user.getHeadimg())
                    .setNickname(user.getNickname())
                    .setGameToken(user.getGameToken())
                    .setGold(user.getGold());
            builder.setUserInfo(userInfoBuilder.build());
            innerBuilder.setResponse(builder.build());
        }
        MessageUtil.send(channel,Action.LoginRes,innerBuilder.build());
    }

    private void loginAccount(Channel channel, LoginRequest request,String sessionId){
        String account = request.getAccount();
        String password = request.getPassword();
    }
}
