package com.roje.game.core.manager;

import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerState;
import com.roje.game.core.server.ServerType;
import com.roje.game.core.util.MessageUtil;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class ServerManager {
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private Map<Integer, ServerInfo> serverMap = new ConcurrentHashMap<>();
    private static final AttributeKey<ServerInfo> SERVER_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.server_info");
    private BaseInfo baseInfo;

    public ServerManager(BaseInfo baseInfo){
        this.baseInfo = baseInfo;
    }

    public ServerInfo registerServer(CommonMessage.ServerInfo info, Channel channel) {
        ServerInfo serverInfo = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfo != null){
            log.warn("不能重复注册");
            return null;
        }
        ServerType type = ServerType.valueOf(info.getType());
        if (type == ServerType.UnKnown)
            log.warn("服务器类型未知");
        else
            log.info("服务器类型{}",type.name());
        serverInfo = new ServerInfo();
        if (baseInfo.getType() == ServerType.Cluster){
            int id = idGenerator.getAndIncrement();
            serverInfo.setId(id);
        }else
            serverInfo.setId(info.getId());
        serverInfo.setHttpPort(info.getHttpport());
        serverInfo.setIp(info.getIp());
        serverInfo.setMaxUserCount(info.getMaxUserCount());
        serverInfo.setName(info.getName());
        serverInfo.setOnline(info.getOnline());
        serverInfo.setUserPort(info.getUserPort());
        serverInfo.setInnerPort(info.getInnerPort());
        serverInfo.setType(info.getType());
        serverInfo.setWwwip(info.getWwwip());
        serverInfo.setChannel(channel);
        serverInfo.setFreeMemory(info.getFreeMemory());
        serverInfo.setTotalMemory(info.getTotalMemory());
        serverInfo.setClientVersionCode(info.getRequireClientVersion());
        serverInfo.setVersionCode(info.getVersion());
        serverMap.put(serverInfo.getId(), serverInfo);
        channel.attr(SERVER_INFO_ATTRIBUTE_KEY).set(serverInfo);
        return serverInfo;
    }

    public CommonMessage.ServerUpdateResponse updateServer(Channel channel, CommonMessage.ServerInfo info) {
        CommonMessage.ServerUpdateResponse.Builder builder = CommonMessage.ServerUpdateResponse.newBuilder();
        ServerInfo serverInfo = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfo == null){
            log.warn("服务器的注册信息不存在???");
            builder.setSucess(false);
            builder.setMsg("未找到该服务器的信息,是不是没有注册");
        }else {
            serverInfo.setMaxUserCount(info.getMaxUserCount());
            serverInfo.setName(info.getName());
            serverInfo.setOnline(info.getOnline());
            serverInfo.setFreeMemory(info.getFreeMemory());
            serverInfo.setTotalMemory(info.getTotalMemory());
            serverInfo.setClientVersionCode(info.getRequireClientVersion());
            serverInfo.setVersionCode(info.getVersion());
            builder.setSucess(true);
            builder.setMsg("成功");
        }
        return builder.build();
    }

    /**
     * 取消注册服务器
     *
     * @param serverInfo 服务器信息
     * @param channel    客户端channel
     */
    public void unRegister(ServerInfo serverInfo, Channel channel) {
        channel.attr(SERVER_INFO_ATTRIBUTE_KEY).set(null);
        serverMap.remove(serverInfo.getId());
    }

    public ServerInfo getIdleServer(ServerType type, int ver) {
        Optional<ServerInfo> optional = serverMap.values().stream().filter(serverInfo -> serverInfo.getState() == ServerState.NORMAL.getState() &&
                serverInfo.getType() == type.getType() && ver >= serverInfo.getClientVersionCode()
                /*&& serverInfo.getChannel() != null && serverInfo.getChannel().isActive()*/)
                .min(Comparator.comparingInt(ServerInfo::getOnline));
        return optional.orElse(null);
    }

    public List<CommonMessage.ConnInfo> getGateConnInfo(int ver){
        return serverMap.values().stream().filter(serverInfo -> serverInfo.getState() == ServerState.NORMAL.getState() &&
        serverInfo.getType() == ServerType.Gate.getType() &&
        ver >= serverInfo.getClientVersionCode()).map(serverInfo -> {
            CommonMessage.ConnInfo.Builder builder = CommonMessage.ConnInfo.newBuilder();
            builder.setId(serverInfo.getId());
            builder.setType(serverInfo.getType());
            builder.setIp(serverInfo.getIp());
            builder.setPort(serverInfo.getInnerPort());
            return builder.build();
        }).collect(Collectors.toList());
    }

    public void channelInactive(Channel channel) {
        ServerInfo serverInfo = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfo != null) {
            log.warn("服务器:{}:{}断开连接", serverInfo.getName(),serverInfo.getId());
            unRegister(serverInfo,channel);
        }else
            log.warn("未知连接断开");
    }

    public void publishGateServerConnected(CommonMessage.ServerRegisterResponse response,int ver) {
        serverMap.values().stream().filter(serverInfo -> serverInfo.getState() == ServerState.NORMAL.getState() &&
                (serverInfo.getType() == ServerType.Hall.getType() || serverInfo.getType() == ServerType.Game.getType()) &&
                ver >= serverInfo.getClientVersionCode())
                .collect(Collectors.toList())
                .forEach(serverInfo -> serverInfo.send(response));
    }
}
