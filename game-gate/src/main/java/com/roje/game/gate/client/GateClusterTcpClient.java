package com.roje.game.gate.client;


import com.roje.game.core.netty.NettyTcpClient;
import com.roje.game.core.service.ClientService;

public class GateClusterTcpClient extends ClientService {

    public GateClusterTcpClient(NettyTcpClient tcpClient) {
        super(tcpClient);
    }
}
