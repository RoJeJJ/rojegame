package com.roje.game.core.processor.impl;

import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.processor.AbsMessageProcessor;
import com.roje.game.core.processor.dispatcher.MessageDispatcher;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.LoginRequest;
import io.netty.channel.Channel;
import org.apache.commons.lang3.StringUtils;


public class DefaultLoginReqProcessor extends AbsMessageProcessor {

    private final UserRedisService userRedisService;

    private final ISessionManager sessionManager;

    public DefaultLoginReqProcessor(MessageDispatcher dispatcher,
                                    ISessionManager sessionManager,
                                    UserRedisService userRedisService) {
        super(dispatcher);
        this.sessionManager = sessionManager;
        this.userRedisService = userRedisService;
    }

    @Override
    public void handler(Channel channel, Frame frame) throws Exception {
        final LoginRequest loginRequest = frame.getData().unpack(LoginRequest.class);
        String account = loginRequest.getAccount();
        String token = loginRequest.getGameToken();
        if (StringUtils.isBlank(loginRequest.getAccount())){
            MessageUtil.sendErrorData(channel,ErrorData.LOGIN_ACCOUNT_IS_NULL);
            return;
        }
        if (StringUtils.isBlank(token)){
            MessageUtil.sendErrorData(channel,ErrorData.LOGIN_INVALID_TOKEN);
            return;
        }
        String gameToken = userRedisService.getToken(account);
        if (!StringUtils.equals(token,gameToken)){
            MessageUtil.sendErrorData(channel,ErrorData.LOGIN_INVALID_TOKEN);
            return;
        }
        sessionManager.login(channel,account);

    }
}
