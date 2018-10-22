package com.roje.game.cluster.manager;

import com.google.gson.JsonObject;
import com.roje.game.cluster.server.ServerInfo;
import com.roje.game.core.netty.ChannelStatus;
import com.roje.game.core.service.redis.UserRedisService;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.server_info.ServInfoRequest;
import com.roje.game.message.server_info.ServInfoResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import jdk.nashorn.internal.ir.IfNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Slf4j
public class ServerSessionManager {

    private final UserRedisService userRedisService;

    private static final AttributeKey<ChannelStatus> CHANNEL_STATUS_ATTRIBUTE_KEY = AttributeKey.newInstance("netty-channel-status");

    private Map<String, ServerInfo> serverInfoMap = new ConcurrentHashMap<>();

    @Autowired
    public ServerSessionManager(UserRedisService userRedisService) {
        this.userRedisService = userRedisService;
    }


    public void channelActive(Channel channel){
        channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.def);
        channel.eventLoop().schedule(new ConnectionTerminator(channel),20,TimeUnit.SECONDS);
    }

    public void channelInactive(Channel channel){
        serverInfoMap.remove(channel.id().asShortText());
    }

    public void serverInfo(Channel channel, ServInfoRequest info) {
        ServerInfo serverInfo = serverInfoMap.computeIfAbsent(channel.id().asShortText(), s -> new ServerInfo());
        serverInfo.setIp(info.getIp());
        serverInfo.setPort(info.getPort());
        serverInfo.setGameId(info.getGameId());
        serverInfo.setOnline(info.getOnline());
        serverInfo.setMaxUserCount(info.getMaxUserCount());
        serverInfo.setName(info.getName());
        serverInfo.setRequireVersion(info.getRequireVersion());
        channel.attr(CHANNEL_STATUS_ATTRIBUTE_KEY).set(ChannelStatus.success);

        ServInfoResponse.Builder builder = ServInfoResponse.newBuilder();
        builder.setSuccess(true);
        builder.setMsg("更新服务器成功");
        MessageUtil.send(channel, Action.ServInfoRes,builder.build());
    }

    public JsonObject allocateServer(String account,int gameId, int version,String token) {
        JsonObject data = new JsonObject();
        if (token != null && StringUtils.equals(token,userRedisService.getToken(account))){
            data.addProperty("success",false);
            data.addProperty("msg","invalid token");
            return data;
        }
        List<ServerInfo> serverInfos = serverInfoMap.values().stream()
                .filter(serverInfo -> serverInfo.getGameId() == gameId)
                .collect(Collectors.toList());
        if (serverInfos.size() == 0){
            data.addProperty("success",false);
            data.addProperty("msg","no such game");
            return data;
        }
        serverInfos = serverInfos.stream()
                .filter(serverInfo -> serverInfo.getRequireVersion() >= version)
                .collect(Collectors.toList());
        if (serverInfos.size() == 0){
            data.addProperty("success",false);
            data.addProperty("msg","unsupported version");
            return data;
        }
        ServerInfo serverInfo = serverInfos.stream()
                .min(Comparator.comparingInt(ServerInfo::getOnline)).get();
        userRedisService.bindIp(account,serverInfo.getIp());
        data.addProperty("success",true);
        data.add("data",serverInfo.json());
        return data;
    }


    private class ConnectionTerminator implements  Runnable{

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
