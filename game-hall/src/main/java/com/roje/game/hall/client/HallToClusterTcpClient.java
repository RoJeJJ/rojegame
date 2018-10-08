package com.roje.game.hall.client;

import com.roje.game.core.netty.NettyTcpClient;
import com.roje.game.core.service.ClientService;

public class HallToClusterTcpClient extends ClientService {
    public HallToClusterTcpClient(NettyTcpClient tcpClient){
        super(tcpClient);
    }
}
