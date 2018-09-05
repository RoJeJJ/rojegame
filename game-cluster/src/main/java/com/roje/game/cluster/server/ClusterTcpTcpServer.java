package com.roje.game.cluster.server;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import com.roje.game.core.server.ServerInfo;
import com.roje.game.core.service.TcpServerService;
import io.netty.util.AttributeKey;

public class ClusterTcpTcpServer extends TcpServerService {
    public static final AttributeKey<ServerInfo> SERVER_INFO_ATTRIBUTE_KEY = AttributeKey.newInstance("netty.cluster.server_info");
    public ClusterTcpTcpServer(ThreadConfig config, NettyTcpServer tcpServer) {
        super(config, tcpServer);
    }
}
