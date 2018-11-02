package com.roje.game.core.processor.req;

import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.MessageProcessor;
import com.roje.game.core.processor.Processor;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.*;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


@Slf4j
@Processor(action = Action.LoginReq)
public class LoginReqProcessor extends MessageProcessor {

    private final ISessionManager sessionManager;

    private final UserRedisService userRedisService;

    public LoginReqProcessor(ISessionManager sessionManager,
                             UserRedisService userRedisService) {
        this.sessionManager = sessionManager;
        this.userRedisService = userRedisService;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        final LoginRequest loginRequest = frame.getData().unpack(LoginRequest.class);
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        String account = loginRequest.getAccount();
        String token = loginRequest.getGameToken();
        if (StringUtils.isBlank(loginRequest.getAccount())){
            builder.setCode(ErrorData.LOGIN_ACCOUNT_IS_NULL.getCode());
            builder.setMsg(ErrorData.LOGIN_ACCOUNT_IS_NULL.getMsg());
            MessageUtil.send(channel,Action.loginRes,builder.build());
            return;
        }
        if (StringUtils.isBlank(token)){
            builder.setCode(ErrorData.LOGIN_INVALID_TOKEN.getCode());
            builder.setMsg(ErrorData.LOGIN_INVALID_TOKEN.getMsg());
            MessageUtil.send(channel,Action.loginRes,builder.build());
            return;
        }
        String gameToken = userRedisService.getToken(account);
        if (!StringUtils.equals(token,gameToken)){
            builder.setCode(ErrorData.LOGIN_INVALID_TOKEN.getCode());
            builder.setMsg(ErrorData.LOGIN_INVALID_TOKEN.getMsg());
            MessageUtil.send(channel,Action.loginRes,builder.build());
            return;
        }
        sessionManager.login(channel,account);

    }
}
