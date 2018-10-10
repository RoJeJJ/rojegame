package com.roje.game.core.manager;

import com.roje.game.core.config.NettyConnGateClientConfig;
import com.roje.game.core.dispatcher.MessageDispatcher;
import com.roje.game.core.netty.NettyGateTcpClient;
import com.roje.game.core.service.Service;
import com.roje.game.message.common.CommonMessage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConnGateTcpMultiManager {
    private Map<Integer,NettyGateTcpClient> clientMap = new ConcurrentHashMap<>();

    private NettyConnGateClientConfig gateClientConfig;

    private Service service;

    private MessageDispatcher dispatcher;

    public ConnGateTcpMultiManager(NettyConnGateClientConfig gateClientConfig, Service service, MessageDispatcher dispatcher){
        this.gateClientConfig = gateClientConfig;
        this.service = service;
        this.dispatcher = dispatcher;
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

    public Service getService() {
        return service;
    }

    public MessageDispatcher getDispatcher() {
        return dispatcher;
    }
}
