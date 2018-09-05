package com.roje.game.core.service;

import com.roje.game.core.config.ServerConfig;
import com.roje.game.core.config.ThreadConfig;
import com.roje.game.core.netty.NettyHttpServer;

public class HttpServerService extends Service {
    private NettyHttpServer httpServer;
    public HttpServerService(ThreadConfig config, NettyHttpServer httpServer) {
        super(config);
        this.httpServer = httpServer;
    }
    public HttpServerService(NettyHttpServer httpServer){
        this(null,httpServer);
    }

    public void onShutDown(){}

    @Override
    protected void doShutDown() {
        if (httpServer != null)
            httpServer.stop();
        onShutDown();
    }

    @Override
    protected void onRun() {
        if (httpServer != null)
            httpServer.start();
    }

    public ServerConfig serverConfig(){
        return httpServer == null ? null:httpServer.serverConfig();
    }
    public NettyHttpServer httpServer(){
        return httpServer;
    }
}
