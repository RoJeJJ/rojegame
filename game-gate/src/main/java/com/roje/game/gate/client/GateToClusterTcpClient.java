package com.roje.game.gate.client;


import com.roje.game.core.netty.NettyTcpClient;
import com.roje.game.core.service.ClientService;

public class GateToClusterTcpClient extends ClientService {

    public GateToClusterTcpClient(NettyTcpClient tcpClient) {
        super(tcpClient);
    }
}
