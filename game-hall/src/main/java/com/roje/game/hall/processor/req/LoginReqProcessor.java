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
import com.roje.game.message.Mid;
import com.roje.game.message.login.LoginMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@Processor(mid = Mid.MID.LoginReq_VALUE)
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
    public void handler(Channel channel, byte[] bytes) throws Exception {
        LoginMessage.LoginRequest request = LoginMessage.LoginRequest.parseFrom(bytes);
        log.info("请求登录:{},{}",request.getAccount(),request.getPassword());
        switch (request.getLoginType()){
            case WECHAT:
                loginWeChat(channel,request);
                break;
            case ACCOUNT:
                loginAccount(channel,request);
                break;
        }
    }

    private void loginWeChat(Channel channel, LoginMessage.LoginRequest request){
        String openid = request.getAccount();
        String token = request.getPassword();
        JsonObject data = WeChatUtil.userInfo(openid,token);
        LoginMessage.LoginResponse.Builder builder = LoginMessage.LoginResponse.newBuilder();
        builder.setSessionId(request.getSessionId());
        builder.setMid(Mid.MID.LoginRes);
        if (data == null || data.has("errcode")){
            builder.setOk(false);
            builder.setMsg("微信验证失败");
            MessageUtil.send(channel,builder.getMid().getNumber(),0,builder.build().toByteArray());
            return;
        }
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
        builder.setOk(true);
        LoginMessage.UserInfo.Builder userInfoBuilder = LoginMessage.UserInfo.newBuilder();
        userInfoBuilder.setAccount(user.getAccount())
                .setId(user.getId())
                .setHeadimg(user.getHeadimg())
                .setNickname(user.getNickname())
                .setGameToken(user.getGameToken())
                .setGold(user.getGold());
        builder.setInfo(userInfoBuilder.build());
        MessageUtil.send(channel,builder.getMid().getNumber(),user.getId(),builder.build().toByteArray());
    }

    private void loginAccount(Channel channel, LoginMessage.LoginRequest request){
        String account = request.getAccount();
        String password = request.getPassword();
    }
}
