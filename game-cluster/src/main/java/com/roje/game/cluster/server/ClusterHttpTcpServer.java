package com.roje.game.cluster.server;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyHttpServer;
import com.roje.game.core.service.HttpServerService;

public class ClusterHttpTcpServer extends HttpServerService {
    public ClusterHttpTcpServer(ThreadConfig config, NettyHttpServer httpServer) {
        super(config, httpServer);
    }
}
