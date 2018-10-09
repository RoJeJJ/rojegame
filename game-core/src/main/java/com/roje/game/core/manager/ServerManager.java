package com.roje.game.core.manager;

import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerState;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class ServerManager {
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private Map<Integer, ServerInfo> serverMap = new ConcurrentHashMap<>();
    private static final AttributeKey<ServerInfo> SERVER_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.server_info");

    /**
     * 不带ID注册服务器
     *
     * @param info    服务器
     * @param channel 客户端channel
     * @return 服务器id, 0 注册失败
     */
    public int registerServerWithoutID(CommonMessage.ServerInfo info, Channel channel) {
        if (invalidIpAndPort(info.getIp(), info.getPort()))
            return 0;

        int id = idGenerator.getAndIncrement();
        return registerServer(info, id, channel);
    }

    /**
     * 带ID注册服务器
     *
     * @param info    服务器信息
     * @param channel 客户端channel
     * @return 服务器id, 0 注册失败
     */
    public int registerServerWithID(CommonMessage.ServerInfo info, Channel channel) {
        if (invalidIpAndPort(info.getIp(), info.getPort()))
            return 0;
        return registerServer(info, info.getId(), channel);
    }

    private int registerServer(CommonMessage.ServerInfo info, int id, Channel channel) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setId(id);
        serverInfo.setHttpPort(info.getHttpport());
        serverInfo.setIp(info.getIp());
        serverInfo.setMaxUserCount(info.getMaxUserCount());
        serverInfo.setName(info.getName());
        serverInfo.setOnline(info.getOnline());
        serverInfo.setPort(info.getPort());
        serverInfo.setType(info.getType());
        serverInfo.setWwwip(info.getWwwip());
        serverInfo.setChannel(channel);
        serverInfo.setFreeMemory(info.getFreeMemory());
        serverInfo.setTotalMemory(info.getTotalMemory());
        serverInfo.setClientVersionCode(info.getRequireClientVersion());
        serverInfo.setVersionCode(info.getVersion());
        serverMap.put(id, serverInfo);
        channel.attr(SERVER_INFO_ATTRIBUTE_KEY).set(serverInfo);
        return id;
    }

    private boolean invalidIpAndPort(String ip, int port) {
        if (StringUtils.isBlank(ip)) {
            log.warn("ip不能为空");
            return true;
        }
        for (ServerInfo i : serverMap.values()) {
            if (StringUtils.equals(ip, i.getIp()) && port == i.getPort()) {
                log.info("{}/{},该地址的端口已经存在", ip, port);
                return true;
            }
        }
        return false;
    }

    public CommonMessage.ServerUpdateResponse updateServer(CommonMessage.ServerInfo info) {
        CommonMessage.ServerUpdateResponse.Builder builder = CommonMessage.ServerUpdateResponse.newBuilder();
        int id = info.getId();
        ServerInfo serverInfo = serverMap.get(id);
        if (serverInfo == null) {
            log.info("未找到该服务器的信息,是不是没有注册");
            builder.setSucess(false);
            builder.setMsg("未找到该服务器的信息,是不是没有注册");
            return builder.build();
        } else {
            serverInfo.setMaxUserCount(info.getMaxUserCount());
            serverInfo.setName(info.getName());
            serverInfo.setOnline(info.getOnline());
            serverInfo.setFreeMemory(info.getFreeMemory());
            serverInfo.setTotalMemory(info.getTotalMemory());
            serverInfo.setClientVersionCode(info.getRequireClientVersion());
            serverInfo.setVersionCode(info.getVersion());
            builder.setSucess(true);
            builder.setMsg("成功");
            return builder.build();
        }
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

    public void channelInactive(Channel channel) {
        ServerInfo serverInfo = channel.attr(SERVER_INFO_ATTRIBUTE_KEY).get();
        if (serverInfo != null) {
            log.warn("服务器:{}断开连接", serverInfo.getName());
            unRegister(serverInfo,channel);
        }else
            log.warn("未知连接断开");
    }
}
