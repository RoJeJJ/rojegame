package com.roje.game.cluster.manager;

import com.roje.game.cluster.server.ClusterTcpTcpServer;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.server.ServerState;
import com.roje.game.core.server.ServerType;
import com.roje.game.message.common.CommonMessage;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class ServerManager {
    private static final Logger LOG = LoggerFactory.getLogger(ServerManager.class);
    private static AtomicInteger idGenerator = new AtomicInteger(1);
    private Map<Integer,ServerInfo> serverMap = new ConcurrentHashMap<>();

    public int registerServer(CommonMessage.ServerInfo info, ChannelHandlerContext ctx){
        for (ServerInfo i:serverMap.values()){
            if (info.getIp().equals(i.getIp()) && info.getPort() == i.getPort()){
                LOG.info("{}/{},该地址的端口已经存在",info.getIp(),info.getPort());
                return 0;
            }
        }
        int id = idGenerator.getAndIncrement();
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
        serverInfo.setChannel(ctx);
        serverInfo.setFreeMemory(info.getFreeMemory());
        serverInfo.setTotalMemory(info.getTotalMemory());
        serverInfo.setClientVersionCode(info.getRequireClientVersion());
        serverInfo.setVersionCode(info.getVersion());
        serverMap.put(id,serverInfo);
        ctx.channel().attr(ClusterTcpTcpServer.SERVER_INFO_ATTRIBUTE_KEY).set(serverInfo);
        return id;
    }
    public CommonMessage.ServerUpdateResponse updateServer(CommonMessage.ServerInfo info){
        CommonMessage.ServerUpdateResponse.Builder builder = CommonMessage.ServerUpdateResponse.newBuilder();
        int id = info.getId();
        ServerInfo serverInfo = serverMap.get(id);
        if (serverInfo == null) {
            LOG.info("未找到该服务器的信息,是不是没有注册");
            builder.setSucess(false);
            builder.setMsg("未找到该服务器的信息,是不是没有注册");
            return  builder.build();
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

    public void unRegister(ServerInfo serverInfo,ChannelHandlerContext ctx) {
        ctx.channel().attr(ClusterTcpTcpServer.SERVER_INFO_ATTRIBUTE_KEY).set(null);
        serverMap.remove(serverInfo.getId());
    }
    public ServerInfo getIdleGateServer(int ver){
        Optional<ServerInfo> optional = serverMap.values().stream().filter(serverInfo -> serverInfo.getState() == ServerState.NORMAL.getState() &&
                serverInfo.getType() == ServerType.Gate.getType() && ver >= serverInfo.getClientVersionCode())
                .min(Comparator.comparingInt(ServerInfo::getOnline));
        return optional.orElse(null);
    }
}
