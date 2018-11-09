package com.roje.game.cluster.manager;

import com.google.gson.JsonArray;
import com.roje.game.cluster.server.CuServerInfo;
import com.roje.game.core.netty.ChannelStatus;
import com.roje.game.core.redis.service.UserRedisService;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServInfo;
import com.roje.game.message.server_info.ServInfoResponse;
import com.roje.game.message.server_info.ServRegResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;



@Slf4j
public class ServerSessionManager {
    @Getter
    private final Service service;

    private static final AttributeKey<ChannelStatus> CHANNEL_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty-channel-status");

    private Map<String, CuServerInfo> serverInfoMap = new ConcurrentHashMap<>();

    private Map<Integer, CuServerInfo> serverIds = new ConcurrentHashMap<>();

    private Map<Integer, List<CuServerInfo>> typeIds = new ConcurrentHashMap<>();

    private final UserRedisService userRedisService;

    @Autowired
    public ServerSessionManager(Service service,
                                UserRedisService userRedisService) {
        this.service = service;
        this.userRedisService = userRedisService;
    }


    private String id(Channel channel) {
        return channel.id().asLongText();
    }

    private ScheduledExecutorService channelExecutorService(Channel channel) {
        return service.getCustomExecutor("channel").allocateThread(id(channel));
    }

    public ScheduledExecutorService accountExecutorService(String account) {
        return service.getCustomExecutor("account").allocateThread(account);
    }


    public void channelActive(Channel channel) {
        channelExecutorService(channel).execute(() -> {
            channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.def);
            channelExecutorService(channel)
                    .schedule(new ConnectionTerminator(channel), 10, TimeUnit.SECONDS);
        });
    }

    public void channelInactive(Channel channel) {
        CuServerInfo info = serverInfoMap.remove(channel.id().asLongText());
        if (info != null){
            serverIds.remove(info.getId());
            synchronized (Integer.toString(info.getType()).intern()) {
                List<CuServerInfo> list = typeIds.get(info.getType());
                if (list != null)
                    list.remove(info);
            }
        }
    }

    public void serverInfo(Channel channel, ServInfo info) {
        ServInfoResponse.Builder builder = ServInfoResponse.newBuilder();
        channelExecutorService(channel)
                .execute(() -> {
                    CuServerInfo cuServerInfo = serverInfoMap.get(id(channel));
                    if (cuServerInfo == null) {
                        builder.setSuccess(false);
                        builder.setMsg("还没有注册");
                        MessageUtil.send(channel, Action.ServInfoRes, builder.build());
                        return;
                    }
                    cuServerInfo.setIp(info.getIp());
                    cuServerInfo.setPort(info.getPort());
                    cuServerInfo.setType(info.getType());
//                    cuServerInfo.setOnline(info.getOnline());
                    cuServerInfo.setMaxUserCount(info.getMaxUserCount());
                    cuServerInfo.setName(info.getName());
                    cuServerInfo.setRequireVersion(info.getRequireVersion());

                    log.info("{}更新成功", cuServerInfo);
                    builder.setSuccess(true);
                    builder.setMsg("更新成功");
                    MessageUtil.send(channel, Action.ServInfoRes, builder.build());
                });
    }

    public String allocateServer(String account, int version) {
        Iterator<Map.Entry<Integer, List<CuServerInfo>>>
                it = typeIds.entrySet().iterator();
        JsonArray array = new JsonArray();
        while (it.hasNext()) {
            Map.Entry<Integer, List<CuServerInfo>> entry = it.next();
            int gameId = entry.getKey();
            ServerInfo info = userRedisService.getAllocateServer(account, gameId);
            if (info != null) {
                array.add(info.json());
            } else {
                Optional<CuServerInfo> optional =
                        entry.getValue().stream()
                                .filter(cuServerInfo -> cuServerInfo.getRequireVersion() >= version)
                                .min(Comparator.comparingInt(CuServerInfo::getOnline));
                optional.ifPresent(cuServerInfo -> {
                            cuServerInfo.increaseOnline();
                            ServerInfo serverInfo = cuServerInfo.serverInfo();
                            array.add(serverInfo.json());
                            userRedisService.allocateServer(account,serverInfo);
                        }
                );
            }
        }
        return array.toString();
    }

    public void register(Channel channel, ServInfo servInfo) {
        ServRegResponse.Builder builder = ServRegResponse.newBuilder();
        channelExecutorService(channel)
                .execute(() -> {
                    CuServerInfo info = serverInfoMap.get(id(channel));
                    if (info != null) {
                        log.info("已经注册过了,不能重复注册,id:{}", info.getId());
                        builder.setSuccess(false);
                        builder.setMsg("已经注册过了");
                        return;
                    }
                    synchronized (Integer.toString(servInfo.getId()).intern()) {
                        CuServerInfo info2 = serverIds.get(servInfo.getId());
                        if (info2 != null) {
                            log.info("id:{}已被注册", servInfo.getId());
                            builder.setSuccess(false);
                            builder.setMsg("id:" + servInfo.getId() + "已被注册");
                            return;
                        }
                        info = new CuServerInfo();
                        info.setIp(servInfo.getIp());
                        info.setPort(servInfo.getPort());
                        info.setType(servInfo.getType());
                        info.setMaxUserCount(servInfo.getMaxUserCount());
                        info.setName(servInfo.getName());
//                        info.setOnline(servInfo.getOnline());
                        info.setOnline(servInfo.getRequireVersion());
                        info.setId(servInfo.getId());

                        serverInfoMap.put(id(channel), info);
                        serverIds.put(info.getId(), info);
                        List<CuServerInfo> list = typeIds.computeIfAbsent(servInfo.getType(), integer -> new ArrayList<>());
                        list.add(info);
                    }

                    ChannelStatus status = channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).get();
                    if (status != ChannelStatus.success)
                        channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.success);
                    log.info("{}注册成功", info);
                    builder.setSuccess(true);
                    builder.setId(info.getId());
                    builder.setMsg("注册成功");
                    MessageUtil.send(channel, Action.ServRegRes, builder.build());
                });
    }


    private class ConnectionTerminator implements Runnable {

        private final Channel channel;

        ConnectionTerminator(Channel channel) {
            this.channel = channel;
        }

        @Override
        public void run() {
            if (channel != null) {
                ChannelStatus status = channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).get();
                if (status == null || status != ChannelStatus.success) {
                    log.warn("{}认证失败,关闭连接");
                    channel.close();
                }
            }
        }
    }
}
