package com.roje.game.hall.processor.req;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.hall.WxUser;
import com.roje.game.hall.utils.WeChatUtil;
import com.roje.game.message.Mid;
import com.roje.game.message.login.LoginMessage;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Processor(mid = Mid.MID.LoginReq_VALUE)
public class LoginReqProcessor extends MessageProcessor {
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
        if (data == null || data.has("errcode")){
            builder.setOk(false);
            builder.setMsg("微信验证失败");
            MessageUtil.send(channel,builder.getMid().getNumber(),0,builder.build().toByteArray());
            return;
        }
        WxUser wxUser = new Gson().fromJson(data,WxUser.class);
    }

    private void loginAccount(Channel channel, LoginMessage.LoginRequest request){

    }
}
