package com.roje.game.core.manager.session;

import com.roje.game.core.entity.role.Role;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.AuthStatus;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class SessionManager implements ISessionManager {

    private final AttributeKey<AuthStatus> AUTH_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.channel.login");

    private final AttributeKey<Role> ROLE_ATTRIBUTE_KEY = AttributeKey.newInstance("roje.channel.role");


    private Map<String, Role> accRolesMap = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    private final AuthLock authLock;

    private final ServerInfo serverInfo;


    public SessionManager(UserRedisService userRedisService,
                          AuthLock authLock, ServerInfo serverInfo) {
        this.userRedisService = userRedisService;
        this.authLock = authLock;
        this.serverInfo = serverInfo;
    }

    public String id(Channel channel) {
        return channel.id().asLongText();
    }

    @Override
    public void sessionOpen(Channel channel) {
        channel.eventLoop().execute(() -> {
            channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.NotAuth);
            channel.eventLoop()
                    .schedule(new DelayRunnable(channel), 10, TimeUnit.SECONDS);
        });
    }

    @Override
    public int getOnlineCount() {
        return accRolesMap.size();
    }

    @Override
    public void sessionClose(Channel channel) {
        // TODO: 2018/10/23 断线了
    }

    @Override
    public void login(Channel channel, String account) {
        channel.eventLoop().execute(() -> {
            RLock aLock = null;
            Role role;
            try {
                AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
                if (status == AuthStatus.Authed) {
                    log.info("already logged");
                    throw new RJException(ErrorData.LOGIN_LOGGED);
                }
                aLock = authLock.getLock(account);
                if (!aLock.tryLock()) {
                    log.info("正在登录另一台服务器");
                    throw new RJException(ErrorData.LOGIN_ANOTHER_SERVER);
                }
                ServerInfo loggedServerInfo = userRedisService.getLoggedServer(account);
                if (loggedServerInfo != null) {
                    if (loggedServerInfo.getId() != serverInfo.getId()) {
                        log.info("已经登录到另一台服务器了");
                        throw new RJException(ErrorData.LOGIN_LOGGED_ANOTHER_SERVER);
                    }
                }
//                ServerInfo allocServerInfo = userRedisService.getAllocateServer(account);
//
//                //玩家跨服
//                boolean cross = false;
//                if (allocServerInfo.getId() != getServerInfo().getId()) {
//                    cross = true;
//                }
                synchronized (account.intern()) {
                    role = accRolesMap.get(account);
                    if (role != null){
                        kickRole(role);
                        channel.close();
                    }
                    role = createRole(account);
                    accRolesMap.put(account,role);
                    role.setChannel(channel);
                }
                userRedisService.bindLoggedServer(account, serverInfo);
                channel.attr(ROLE_ATTRIBUTE_KEY).set(role);
                channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.Authed);
                LoginResponse.Builder builder = LoginResponse.newBuilder();
                builder.setCode(0);
                MessageUtil.send(channel, Action.loginRes, builder.build());
            } catch (RJException e) {
                MessageUtil.sendErrorData(channel,e.getErrorData());
            } finally {
                if (aLock != null)
                    aLock.unlock();
            }
        });
    }

    protected abstract <R extends Role> void kickRole(R role);

    protected abstract <R extends Role> R createRole(String account);

    @SuppressWarnings("unchecked")
    public <R extends Role> R getRole(Channel channel) {
        return (R) channel.attr(ROLE_ATTRIBUTE_KEY).get();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <R extends Role> R getRole(String account) {
        return (R) accRolesMap.get(account);
    }

    private class DelayRunnable implements Runnable {
        private final Channel channel;

        DelayRunnable(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            if (channel != null) {
                synchronized (channel) {
                    AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
                    if (status == null || status != AuthStatus.Authed) {
                        channel.close();
                    }
                }
            }
        }
    }
}
