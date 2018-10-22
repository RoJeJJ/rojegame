package com.roje.game.core.manager.session;

import com.roje.game.core.config.GameProperties;
import com.roje.game.core.entity.Role;
import com.roje.game.core.entity.Room;
import com.roje.game.core.entity.User;
import com.roje.game.core.exception.ErrorData;
import com.roje.game.core.exception.RJException;
import com.roje.game.core.manager.room.RoomManager;
import com.roje.game.core.manager.session.ISessionManager;
import com.roje.game.core.server.AuthStatus;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.thread.executor.TaskExecutor;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.create_room.CreateCardRoomResponse;
import com.roje.game.message.frame.Frame;
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
public abstract class SessionManager<R extends Role<M>,M extends Room> implements ISessionManager {

    private final AttributeKey<AuthStatus> AUTH_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.channel.login");

    private final AttributeKey<R> ROLE_ATTRIBUTE_KEY = AttributeKey.newInstance("roje.channel.role");

    private ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private Map<String, R> accRolesMap = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    private final ServerInfo serverInfo;

    private final TaskExecutor<String> userChannelExecutor;


    private final RoomManager<M,R> roomManager;

    public SessionManager(UserRedisService userRedisService,
                          ServerInfo info,
                          TaskExecutor<String> userChannelExecutor,
                          RoomManager<M,R> roomManager){
        this.userRedisService = userRedisService;
        this.serverInfo = info;
        this.userChannelExecutor = userChannelExecutor;
        this.roomManager = roomManager;
    }

    public String id(Channel channel){
        return channel.id().asShortText();
    }

    public void sessionActive(Channel channel){
        userChannelExecutor.allocateThread(id(channel)).execute(() -> {
            channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.NotAuth);
            channels.add(channel);
//            channel.eventLoop().schedule(new DelayRunnable(channel),10, TimeUnit.SECONDS);
            userChannelExecutor.allocateThread(id(channel))
                    .schedule(new DelayRunnable(channel),10, TimeUnit.SECONDS);
        });
    }

    @Override
    public int getOnlineCount() {
        return accRolesMap.size();
    }

    public void sessionInactive(Channel channel) {
        channels.remove(channel);
    }

    public void login(Channel channel, LoginRequest loginRequest) {
        LoginResponse.Builder builder = LoginResponse.newBuilder();
        userChannelExecutor.allocateThread(id(channel)).execute(() -> {
            try {
                AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
                if (status == AuthStatus.Authed) {
                    log.warn("already logged");
                    throw new RJException(ErrorData.LOGIN_LOGGED_ALREADY);
                }
                String account = loginRequest.getAccount();
                String token = loginRequest.getGameToken();
                if (StringUtils.isBlank(account) || StringUtils.isBlank(token)) {
                    log.warn("account:{},token:{}", account, token);
                    throw new RJException(ErrorData.LOGIN_PARAMS_NOT_BE_EMPTY);
                }
                if (!StringUtils.equals(serverInfo.getIp(), userRedisService.getIp(account))) {
                    log.warn("连接到错误的服务器");
                    throw new RJException(ErrorData.LOGIN_INVALID_CONNECTION);
                }
                User user = userRedisService.get(account);
                if (user == null) {
                    log.warn("用户名:{}不存在", account);
                    throw new RJException(ErrorData.LOGIN_BAD_USERNAME);
                }
                String userToken = userRedisService.getToken(account);
                if (!StringUtils.equals(token, userToken)) {
                    log.warn("user's token:{},system token:{}", token, userToken);
                    throw new RJException(ErrorData.LOGIN_BAD_TOKEN);
                }
                R role;
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
                }
                channel.attr(ROLE_ATTRIBUTE_KEY).set(role);
                channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).set(AuthStatus.Authed);
                builder.setCode(0);
            }catch (RJException e){
                builder.setCode(e.getErrorData().getCode());
                builder.setMsg(e.getErrorData().getMsg());
            }finally {
                MessageUtil.send(channel,Action.loginRes,builder.build());
            }
        });
    }

    public abstract R createRole();

    private R getRole(Channel channel){
        return channel.attr(ROLE_ATTRIBUTE_KEY).get();
    }

    public boolean isLogin(Channel channel) {
        AuthStatus status = channel.attr(AUTH_STATUS_ATTRIBUTE_KEY).get();
        return status == AuthStatus.Authed;
    }

    public void createRoom(Channel channel, Frame frame) {
        userChannelExecutor.allocateThread(id(channel)).execute(() -> {
            try {
                if (!isLogin(channel)) {
                    log.info("还没有登录");
                    throw new RJException(ErrorData.NOT_LOGGED_IN);
                }
                R role = getRole(channel);
                M room = roomManager.startCreateRoom(role,frame);
            }catch (RJException e){
                CreateCardRoomResponse.Builder builder = CreateCardRoomResponse.newBuilder();
                builder.setCode(e.getErrorData().getCode());
                builder.setMsg(e.getErrorData().getMsg());
            }
        });
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
