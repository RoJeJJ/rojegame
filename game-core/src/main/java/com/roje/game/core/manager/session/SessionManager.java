package com.roje.game.core.manager.session;

import com.roje.game.core.entity.Role;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.room.RoomHelper;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.AuthStatus;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomRequest;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class SessionManager<R extends Role,M> implements ISessionManager<R,M> {

    private final AttributeKey<AuthStatus> AUTH_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.channel.login");

    private final AttributeKey<R> ROLE_ATTRIBUTE_KEY = AttributeKey.newInstance("roje.channel.role");


    private Map<String, R> accRolesMap = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    private final RoomHelper<R,M> roomHelper;

    private final AuthLock authLock;

    private final ServerInfo serverInfo;

    private final Service service;

    public SessionManager(UserRedisService userRedisService,
                          RoomHelper<R,M> roomHelper,
                          AuthLock authLock, ServerInfo serverInfo, Service service) {
        this.userRedisService = userRedisService;
        this.roomHelper = roomHelper;
        this.authLock = authLock;
        this.serverInfo = serverInfo;
        this.service = service;
    }


    private ScheduledExecutorService channelExecutorService(Channel channel) {
        return service.getCustomExecutor("channel").allocateThread(id(channel));
    }

    private ScheduledExecutorService accountExecutorService(String account) {
        return service.getCustomExecutor("account").allocateThread(account);
    }

    public String id(Channel channel) {
        return channel.id().asLongText();
    }

    @Override
    public void sessionOpen(Channel channel) {
        channelExecutorService(channel)
                .execute(() -> {
                    channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.NotAuth);
                    channelExecutorService(channel)
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
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        channelExecutorService(channel).execute(() -> {
            RLock aLock = null;
            R role;
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
                    }
                    role = createRole(account);
                    accRolesMap.put(account,role);
                    role.setChannel(channel);
                }
                userRedisService.bindLoggedServer(account, serverInfo);
                channel.attr(ROLE_ATTRIBUTE_KEY).set(role);
                channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.Authed);
                builder.setCode(0);
            } catch (RJException e) {
                builder.setCode(e.getErrorData().getCode());
                builder.setMsg(e.getErrorData().getMsg());
            } finally {
                MessageUtil.send(channel, Action.loginRes, builder.build());
                if (aLock != null)
                    aLock.unlock();
            }
        });
    }

    protected abstract void kickRole(R role);

    public abstract R createRole(String account);

    public R getRole(Channel channel) {
        return channel.attr(ROLE_ATTRIBUTE_KEY).get();
    }

    @Override
    public R getRole(String account) {
        return accRolesMap.get(account);
    }

    public boolean isLogin(Channel channel) {
        AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
        return status == AuthStatus.Authed;
    }

    @Override
    public M createRoom(CreateCardRoomRequest request) throws RJException{
        return roomHelper.createRoom(request);
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
