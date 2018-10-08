package com.roje.game.hall.client;

import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpClient;
import com.roje.game.core.service.ClientService;

public class HallToGateTcpClient extends ClientService {
    public HallToGateTcpClient(ThreadConfig threadConfig,NettyTcpClient tcpClient) {
        super(threadConfig,tcpClient);
    }
}
