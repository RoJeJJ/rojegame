package com.roje.game.core.manager;

import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.NettyGateTcpClient;
import com.roje.game.core.server.BaseInfo;
import com.roje.game.core.service.Service;
import com.roje.game.message.common.CommonMessage;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnGateTcpMultiManager {
    private Map<Integer,NettyGateTcpClient> clientMap = new ConcurrentHashMap<>();

    private NettyConnGateClientConfig gateClientConfig;

    @Getter
    private Service service;

    @Getter
    private BaseInfo baseInfo;

    @Getter
    private MessageDispatcher dispatcher;

    @Getter
    private SessionManager sessionManager;

    public ConnGateTcpMultiManager(NettyConnGateClientConfig gateClientConfig, Service service, MessageDispatcher dispatcher, BaseInfo baseInfo, SessionManager sessionManager){
        this.gateClientConfig = gateClientConfig;
        this.service = service;
        this.dispatcher = dispatcher;
        this.baseInfo = baseInfo;
        this.sessionManager = sessionManager;
    }

    public synchronized void addClient(NettyGateTcpClient client){
        clientMap.putIfAbsent(client.getConnInfo().getId(),client);
    }

    public synchronized void removeClient(int id){
        clientMap.remove(id);
    }

    public void connect(CommonMessage.ConnInfo connInfo){
        new NettyGateTcpClient(this,gateClientConfig,connInfo).start();
    }
}
