package com.roje.game.core.manager;

import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.action.Action;
import com.roje.game.message.broadcast.GateConnected;
import com.roje.game.message.conn_info.ConnInfo;
import com.roje.game.message.server_info.ServerInfo;
import com.roje.game.message.server_info.ServerStatus;
import com.roje.game.message.server_info.ServerType;
import com.roje.game.message.server_register.ServerRegResponse;
import com.roje.game.message.server_update.ServerUpdateResponse;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ServerManager {
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private Map<Integer, ServerInfo.Builder> serverMap = new ConcurrentHashMap<>();
    private Map<Integer, Channel> channelMap = new ConcurrentHashMap<>();
    private static final AttributeKey<ServerInfo.Builder> SERVER_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.server_info");
    private BaseInfo baseInfo;

    public ServerManager(BaseInfo baseInfo) {
        this.baseInfo = baseInfo;
    }

    public void registerServer(ServerInfo serverInfo, Channel channel) {
        ServerRegResponse.Builder respBuilder = ServerRegResponse.newBuilder();
        ServerInfo.Builder temp = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (temp != null) {
            log.warn("不能重复注册");
            respBuilder.setSuccess(false);
            respBuilder.setMsg("不能重复注册");
            return;
        }
        log.info("服务器类型{}", serverInfo.getType().name());
        ServerInfo.Builder builder = serverInfo.toBuilder();
        if (baseInfo.getType() == ServerType.Cluster) {
            int id = idGenerator.getAndIncrement();
            builder.setId(id);
        }
        channelMap.put(builder.getId(), channel);
        serverMap.put(builder.getId(), builder);
        channel.attr(SERVER_INFO_ATTRIBUTE_KEY).set(builder);
        respBuilder.setSuccess(true);
        respBuilder.setMsg("成功");
        respBuilder.setId(builder.getId());
        respBuilder.setType(baseInfo.getType());
        if (serverInfo.getType() == ServerType.Hall || serverInfo.getType() == ServerType.Game) {
            respBuilder.addAllGateInfo(getGateConnInfo(serverInfo.getVersion()));
        }
        log.info("{}注册到集群服务器成功", serverInfo.getName());

        MessageUtil.send(channel, Action.ServerRegRes, respBuilder.build());

        //如果网关服务器连接,把连接信息广播给其他服务器
        if (serverInfo.getType() == ServerType.Gate) {
            GateConnected.Builder connBuilder = GateConnected.newBuilder();
            ConnInfo.Builder b = ConnInfo.newBuilder();
            b.setIp(builder.getIp());
            b.setPort(builder.getInnerPort());
            b.setId(builder.getId());
            connBuilder.setConnInfo(b.build());
            publishGateServerConnected(connBuilder.build(), serverInfo.getVersion());
        }
    }

    public void updateServer(Channel channel, ServerInfo info) {
        ServerUpdateResponse.Builder builder = ServerUpdateResponse.newBuilder();
        ServerInfo.Builder tempBuilder = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (tempBuilder == null) {
            log.warn("服务器的注册信息不存在???");
            builder.setSuccess(false);
            builder.setMsg("未找到该服务器的信息,是不是没有注册");
        } else {
            tempBuilder.setMaxUserCount(info.getMaxUserCount());
            tempBuilder.setName(info.getName());
            tempBuilder.setOnline(info.getOnline());
            tempBuilder.setFreeMemory(info.getFreeMemory());
            tempBuilder.setTotalMemory(info.getTotalMemory());
            tempBuilder.setRequireClientVersion(info.getRequireClientVersion());
            tempBuilder.setVersion(info.getVersion());
            builder.setSuccess(true);
            builder.setMsg("成功");
        }
        MessageUtil.send(channel, Action.ServerUpdateRes, builder.build());
    }

    /**
     * 取消注册服务器
     *
     * @param id      服务器ID
     * @param channel 客户端channel
     */
    private void unRegister(int id, Channel channel) {
        channel.attr(SERVER_INFO_ATTRIBUTE_KEY).set(null);
        serverMap.remove(id);
        channelMap.remove(id);
    }

    public ServerInfo getIdleServer(ServerType type, int ver) {
        Optional<ServerInfo.Builder> optional =
                serverMap
                        .values()
                        .stream()
                        .filter(builder -> builder.getState() == ServerStatus.Normal &&
                                builder.getType() == type && ver >= builder.getRequireClientVersion())
                        .min(Comparator.comparingInt(ServerInfo.Builder::getOnline));
        if (optional.isPresent()) {
            ServerInfo.Builder builder = optional.get();
            return builder.build();
        } else
            return null;
    }

    public ServerInfo getIdleGameServer(int gameId,int ver){
        Optional<ServerInfo.Builder> optional =
                serverMap
                        .values()
                        .stream()
                        .filter(builder -> builder.getState() == ServerStatus.Normal &&
                                builder.getType() == ServerType.Game &&
                                builder.getGameId() == gameId &&
                                ver >= builder.getRequireClientVersion())
                        .min(Comparator.comparingInt(ServerInfo.Builder::getOnline));
        if (optional.isPresent()) {
            ServerInfo.Builder builder = optional.get();
            return builder.build();
        } else
            return null;
    }

    private List<ConnInfo> getGateConnInfo(int ver) {
        return serverMap.values().stream().filter(builder -> builder.getState() == ServerStatus.Normal &&
                builder.getType() == ServerType.Gate &&
                ver >= builder.getRequireClientVersion()).map(builder -> {
            ConnInfo.Builder connBuilder = ConnInfo.newBuilder();
            connBuilder.setId(builder.getId());
            connBuilder.setIp(builder.getIp());
            connBuilder.setPort(builder.getInnerPort());
            return connBuilder.build();
        }).collect(Collectors.toList());
    }

    public void channelInactive(Channel channel) {
        ServerInfo.Builder serverInfoBuilder = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfoBuilder != null) {
            log.warn("服务器:{}:{}断开连接", serverInfoBuilder.getName(), serverInfoBuilder.getId());
            unRegister(serverInfoBuilder.getId(), channel);
        } else
            log.warn("未知连接断开");
    }

    private void publishGateServerConnected(GateConnected gateConnected, int ver) {
        serverMap.values().stream().filter(serverInfoBuilder -> serverInfoBuilder.getState() == ServerStatus.Normal &&
                (serverInfoBuilder.getType() == ServerType.Hall || serverInfoBuilder.getType() == ServerType.Game) &&
                ver >= serverInfoBuilder.getRequireClientVersion())
                .collect(Collectors.toList())
                .forEach(serverInfoBuilder -> {
                    Channel channel = channelMap.get(serverInfoBuilder.getId());
                    MessageUtil.send(channel, Action.PubGateConnected, gateConnected);
                });
    }

    public Channel getChannel(int id) {
        return channelMap.get(id);
    }
}
