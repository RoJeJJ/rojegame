package com.roje.game.core.manager;

import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.User;
import com.roje.game.core.server.AuthStatus;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class SessionManager<T extends Role> implements ISessionManager {

    private static final AttributeKey<AuthStatus> AUTH_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.channel.login");

    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Map<String, T> roleMap = new ConcurrentHashMap<>();

    private UserRedisService userRedisService;

    public SessionManager(UserRedisService userRedisService){
        this.userRedisService = userRedisService;
    }

    public void sessionActive(Channel channel){
        channel.eventLoop().execute(() -> {
            channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.NotAuth);
            channel.eventLoop().schedule(new DelayRunnable(channel),10, TimeUnit.SECONDS);
            channels.add(channel);
        });

    }

    @Override
    public int getOnlineCount() {
        return 0;
    }

    public void sessionInactive(Channel channel) {
        channels.remove(channel);
    }

    public void login(Channel channel, LoginRequest loginRequest) {
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        channel.eventLoop().execute(() -> {
            AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
            if (status == AuthStatus.Authed){
                log.warn("already logged");
                builder.setSuccess(false);
                builder.setMsg("already logged");
                MessageUtil.send(channel, Action.loginRes,builder.build());
                return;
            }
            String account = loginRequest.getAccount();
            String token = loginRequest.getGameToken();
            if (StringUtils.isBlank(account) || StringUtils.isBlank(token)){
                log.warn("account:{},token:{}",account,token);
                builder.setSuccess(false);
                builder.setMsg("参数错误");
                MessageUtil.send(channel, Action.loginRes,builder.build());
                return;
            }
            User user = userRedisService.get(account);
            if (user == null){
                log.warn("用户名:{}不存在",account);
                builder.setSuccess(false);
                builder.setMsg("用户名不存在");
                MessageUtil.send(channel, Action.loginRes,builder.build());
                return;
            }
            String userToken = userRedisService.getToken(account);
            if (!StringUtils.equals(token,userToken)){
                log.warn("user's token:{},system token:{}",token,userToken);
                builder.setSuccess(false);
                builder.setMsg("invalid token");
                MessageUtil.send(channel, Action.loginRes,builder.build());
                return;
            }
            T role = roleMap.get(account);
            if (role == null){
                role = createRole();
            }

            channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.Authed);
            builder.setSuccess(true);
            MessageUtil.send(channel, Action.loginRes,builder.build());
        });
    }

    public abstract T createRole();

    public boolean isLogin(Channel channel) {
        AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
        return status == AuthStatus.Authed;
    }

    private class DelayRunnable implements Runnable {
        private final Channel channel;
        DelayRunnable(Channel channel){
            this.channel = channel;
        }

        @Override
        public void run() {
            if (channel != null){
                synchronized (channel){
                    AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
                    if (status == null || status != AuthStatus.Authed) {
                        channel.close();
                    }
                }
            }
        }
    }
}
