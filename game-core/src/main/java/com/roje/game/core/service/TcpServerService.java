package com.roje.game.core.service;

import com.roje.game.core.config.ServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyTcpServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TcpServerService extends Service {
    private static final Logger LOG = LoggerFactory.getLogger(TcpServerService.class);
    private NettyTcpServer tcpServer;
//    private TimerEvent timerEvent;

    public TcpServerService(ThreadConfig config, NettyTcpServer tcpServer){
        super(config);
        this.tcpServer = tcpServer;
//        this.timerEvent = null;
    }
    public TcpServerService(NettyTcpServer tcpServer){
        this(null,tcpServer);
    }
    public void onShutDown(){}
    @Override
    protected void doShutDown() {
        if (tcpServer != null)
            tcpServer.stop();
        onShutDown();
    }

    @Override
    protected void onRun() {
//        ServerThread serverThread = getExecutor(ThreadType.sync);
//        if (timerEvent != null && serverThread != null && serverThread.getTimeInterval() > 0)
//            serverThread.addTimerEvent(timerEvent);
        if (tcpServer != null)
            tcpServer.start();
    }
    public ServerConfig serverConfig(){
        return tcpServer == null ? null:tcpServer.getServerConfig();
    }

    public NettyTcpServer tcpServer() {
        return tcpServer;
    }
}
