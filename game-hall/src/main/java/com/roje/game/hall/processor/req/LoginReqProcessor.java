package com.roje.game.hall.processor.req;

import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.thread.executor.RJExecutor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.hall.manager.HallUserManager;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.*;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Slf4j
@Component
@Processor(action = Action.LoginReq)
public class LoginReqProcessor extends MessageProcessor {
    private HallUserManager userManager;

    private final RJExecutor userExecutor;

    @Autowired
    public LoginReqProcessor(HallUserManager userManager,
                             RJExecutor userExecutor) {
        this.userManager = userManager;
        this.userExecutor = userExecutor;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        final InnerLoginRequest innerLoginRequest = frame.getData().unpack(InnerLoginRequest.class);
        final LoginRequest loginRequest = innerLoginRequest.getRequest();
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        boolean invp = false;
        if (StringUtils.isBlank(loginRequest.getAccount())){
            invp = true;
            builder.setSuccess(false);
            builder.setMsg("用户名不能为空");
        }else if (StringUtils.isBlank(loginRequest.getPassword())){
            invp = true;
            builder.setSuccess(false);
            builder.setMsg("密码不能为空");
        }else if (loginRequest.getLoginType() == LoginType.Login_Type_Default){
            invp = true;
            builder.setSuccess(false);
            builder.setMsg("登录方式错误");
        }
        if (invp){
            InnerLoginResponse.Builder ilb = InnerLoginResponse.newBuilder();
            ilb.setResponse(builder);
            ilb.setSessionId(innerLoginRequest.getSessionId());
            MessageUtil.send(channel,Action.LoginRes,ilb.build());
        }else {
            ExecutorService service = userExecutor.allocateThread(loginRequest.getAccount());
            if (service != null){
                service.execute(() -> userManager.login(channel,loginRequest,innerLoginRequest.getSessionId()));
            }
        }
    }
}
