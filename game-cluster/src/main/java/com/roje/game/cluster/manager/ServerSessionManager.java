package com.roje.game.cluster.manager;

import com.google.gson.JsonObject;
import com.roje.game.cluster.server.CuServerInfo;
import com.roje.game.core.netty.ChannelStatus;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.Service;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServInfo;
import com.roje.game.message.server_info.ServInfoRequest;
import com.roje.game.message.server_info.ServInfoResponse;
import com.roje.game.message.server_info.ServRegResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Slf4j
public class ServerSessionManager {

    private static final AtomicInteger COUNTER = new AtomicInteger(1);

    private final UserRedisService userRedisService;

    @Getter
    private final Service service;

    private static final AttributeKey<ChannelStatus> CHANNEL_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty-channel-status");

    private Map<String, CuServerInfo> serverInfoMap = new ConcurrentHashMap<>();

    @Autowired
    public ServerSessionManager(UserRedisService userRedisService, Service service) {
        this.userRedisService = userRedisService;
        this.service = service;
    }


    private String id(Channel channel){
        return channel.id().asShortText();
    }

    private ScheduledExecutorService channelExecutorService(Channel channel){
        return service.getCustomExecutor("channel").allocateThread(id(channel));
    }


    public void channelActive(Channel channel){
        channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.def);
        channelExecutorService(channel)
                .schedule(new ConnectionTerminator(channel),10, TimeUnit.SECONDS);
    }

    public void channelInactive(Channel channel){
        serverInfoMap.remove(channel.id().asShortText());
    }

    public void serverInfo(Channel channel, ServInfo info) {
        ServInfoResponse.Builder builder = ServInfoResponse.newBuilder();
        channelExecutorService(channel)
                .execute(() -> {
                    CuServerInfo cuServerInfo = serverInfoMap.get(id(channel));
                    if (cuServerInfo == null){
                        builder.setSuccess(false);
                        builder.setMsg("还没有注册");
                        MessageUtil.send(channel,Action.ServInfoRes,builder.build());
                        return;
                    }
                    cuServerInfo.setIp(info.getIp());
                    cuServerInfo.setPort(info.getPort());
                    cuServerInfo.setGameId(info.getGameId());
                    cuServerInfo.setOnline(info.getOnline());
                    cuServerInfo.setMaxUserCount(info.getMaxUserCount());
                    cuServerInfo.setName(info.getName());
                    cuServerInfo.setRequireVersion(info.getRequireVersion());

                    builder.setSuccess(true);
                    builder.setMsg("更新成功");
                    MessageUtil.send(channel,Action.ServInfoRes,builder.build());
                });
    }

    public JsonObject allocateServer(String account,int gameId, int version,String token) {
        JsonObject data = new JsonObject();
        if (token != null && StringUtils.equals(token,userRedisService.getToken(account))){
            data.addProperty("success",false);
            data.addProperty("msg","invalid token");
            return data;
        }
        ServerInfo info = userRedisService.getServer(account);
        if (info == null) {
            List<CuServerInfo> cuServerInfos = serverInfoMap.values().stream()
                    .filter(cuServerInfo -> cuServerInfo.getGameId() == gameId)
                    .collect(Collectors.toList());
            if (cuServerInfos.size() == 0) {
                data.addProperty("success", false);
                data.addProperty("msg", "no such game");
                return data;
            }
            cuServerInfos = cuServerInfos.stream()
                    .filter(cuServerInfo -> cuServerInfo.getRequireVersion() >= version)
                    .collect(Collectors.toList());
            if (cuServerInfos.size() == 0) {
                data.addProperty("success", false);
                data.addProperty("msg", "unsupported version");
                return data;
            }
            CuServerInfo cuServerInfo = cuServerInfos.stream()
                    .min(Comparator.comparingInt(CuServerInfo::getOnline)).get();
            info = cuServerInfo.toServerInfo();
            userRedisService.bindServer(account, info);
        }
        data.addProperty("success",true);
        data.add("data",info.json());
        return data;
    }

    public void register(Channel channel, ServInfo servInfo) {
        ServRegResponse.Builder builder = ServRegResponse.newBuilder();
        channelExecutorService(channel)
                .execute(() -> {
                    CuServerInfo info = serverInfoMap.get(id(channel));
                    if (info != null){
                        log.info("已经注册过了,不能重复注册,id:{}",info.getId());
                        builder.setSuccess(false);
                        builder.setMsg("已经注册过了");
                        return;
                    }
                    info = new CuServerInfo();
                    info.setIp(servInfo.getIp());
                    info.setPort(servInfo.getPort());
                    info.setGameId(servInfo.getGameId());
                    info.setMaxUserCount(servInfo.getMaxUserCount());
                    info.setName(servInfo.getName());
                    info.setOnline(servInfo.getOnline());
                    info.setOnline(servInfo.getRequireVersion());
                    info.setId(COUNTER.getAndIncrement());

                    serverInfoMap.put(id(channel),info);

                    ChannelStatus status = channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).get();
                    if (status != ChannelStatus.success)
                        channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.success);

                    builder.setSuccess(true);
                    builder.setId(info.getId());
                    builder.setMsg("更新服务器成功");
                    MessageUtil.send(channel, Action.ServInfoRes,builder.build());
                });
    }


    private class ConnectionTerminator implements Runnable {

        private final Channel channel;

        ConnectionTerminator(Channel channel){
            this.channel = channel;
        }

        @Override
        public void run() {
            if (channel != null){
                ChannelStatus status = channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).get();
                if (status == null || status != ChannelStatus.success) {
                    log.warn("{}认证失败,关闭连接");
                    channel.close();
                }
            }
        }
    }
}
