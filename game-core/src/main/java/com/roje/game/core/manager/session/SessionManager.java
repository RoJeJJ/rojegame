package com.roje.game.core.manager.session;

import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.entity.User;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.redis.lock.AuthLock;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.server.AuthStatus;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import com.roje.game.message.frame.Frame;
import com.roje.game.message.login.LoginRequest;
import com.roje.game.message.login.LoginResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
public abstract class SessionManager<R extends Role<M>, M extends Room> implements ISessionManager {

    private final AttributeKey<AuthStatus> AUTH_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.channel.login");

    private final AttributeKey<R> ROLE_ATTRIBUTE_KEY = AttributeKey.newInstance("roje.channel.role");


    private Map<String, R> accRolesMap = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    private final RoomManager<M, R> roomManager;

    private final AuthLock authLock;

    public SessionManager(UserRedisService userRedisService,
                          RoomManager<M, R> roomManager, AuthLock authLock) {
        this.userRedisService = userRedisService;
        this.roomManager = roomManager;
        this.authLock = authLock;
    }

    private ScheduledExecutorService channelExecutorService(Channel channel) {
        return roomManager.getService().getCustomExecutor("channel").allocateThread(id(channel));
    }

    private ScheduledExecutorService roleExecutorService(R role) {
        return roomManager.getService().getCustomExecutor("role").allocateThread(role.getAccount());
    }

    public String id(Channel channel) {
        return channel.id().asLongText();
    }

    public void sessionActive(Channel channel) {
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

    public void sessionInactive(Channel channel) {
        // TODO: 2018/10/23 断线了
    }

    public void login(Channel channel, LoginRequest loginRequest) throws RJException{
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        String account = loginRequest.getAccount();
        String token = loginRequest.getGameToken();
        if (StringUtils.isBlank(account) || StringUtils.isBlank(token)) {
            log.info("account:{},token:{}", account, token);
            throw new RJException(ErrorData.LOGIN_PARAMS_NOT_BE_EMPTY);
        }
        User user = userRedisService.get(account);
        if (user == null) {
            log.info("用户:{}不存在", account);
            throw new RJException(ErrorData.LOGIN_BAD_USERNAME);
        }
        String userToken = userRedisService.getToken(account);
        if (!StringUtils.equals(token, userToken)) {
            log.info("user's token:{},system token:{}", token, userToken);
            throw new RJException(ErrorData.LOGIN_BAD_TOKEN);
        }
        channelExecutorService(channel).execute(() -> {
            RLock aLock = null;
            R role;
            try {
                AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
                if (status == AuthStatus.Authed) {
                    log.info("already logged");
                    throw new RJException(ErrorData.LOGIN_LOGGED_ALREADY);
                }
                aLock = authLock.getLock(account);
                if (!aLock.tryLock()) {
                    log.info("正在登录另一台服务器");
                    throw new RJException(ErrorData.LOGIN_OTHER_CONNECTION_ACTIVE);
                }
                ServerInfo loggedServerInfo = userRedisService.getLoggedServer(account);
                if (loggedServerInfo != null) {
                    if (loggedServerInfo.getId() != roomManager.getServerInfo().getId()) {
                        log.info("已经登录到另一台服务器了");
                        throw new RJException(ErrorData.LOGIN_ALREADY_CONNECT_ANOTHER_SERVER);
                    }
                }
                ServerInfo allocServerInfo = userRedisService.getAllocateServer(account);

                //玩家跨服
                boolean cross = false;
                if (allocServerInfo.getId() != roomManager.getServerInfo().getId()) {
                    cross = true;
                }
                synchronized (account.intern()) {
                    role = accRolesMap.get(account);
                    if (role == null) {
                        role = createRole();
                        accRolesMap.put(account, role);
                    } else if (role.isOnline()) {
                        // TODO: 2018/10/22 如果role在线,踢下线
                    }
                    role.setAccount(user.getAccount());
                    role.setOnline(true);
                    role.setChannel(channel);
                    role.setCross(cross);
                }
                userRedisService.bindLoggedServer(account, roomManager.getServerInfo());
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

    public abstract R createRole();

    private R getRole(Channel channel) {
        return channel.attr(ROLE_ATTRIBUTE_KEY).get();
    }

    public boolean isLogin(Channel channel) {
        AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
        return status == AuthStatus.Authed;
    }

    public void createCardRoom(Channel channel, Frame frame) throws RJException {
        CreateCardRoomResponse.Builder builder = CreateCardRoomResponse.newBuilder();
        R role = getRole(channel);
        if (role == null) {
            throw new RJException(ErrorData.NOT_LOGGED_IN);
        }
        roleExecutorService(role).execute(() -> {
            try {
//                if (!isLogin(channel)) {
//                    log.info("还没有登录");
//                    throw new RJException(ErrorData.NOT_LOGGED_IN);
//                }
                M room = roomManager.createCardRoom(role, frame);
                builder.setCode(0);
                builder.setRoomId(room.getId());
            } catch (RJException e) {
                builder.setCode(e.getErrorData().getCode());
                builder.setMsg(e.getErrorData().getMsg());
            } finally {
                MessageUtil.send(channel, Action.CreateCardRoomRes, builder.build());
            }
        });
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
