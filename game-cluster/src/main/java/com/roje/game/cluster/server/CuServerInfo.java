package com.roje.game.cluster.server;

import com.google.gson.annotations.Expose;
import com.roje.game.core.server.ServerInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuServerInfo {
    private int id;
    private String ip;
    private int port;
    private int type;
    @Expose private int maxUserCount;
    private String name;
    @Expose private int requireVersion;
    @Expose private int online;

    public ServerInfo serverInfo(){
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setId(id);
        serverInfo.setIp(ip);
        serverInfo.setPort(port);
        serverInfo.setType(type);
        serverInfo.setMaxUserCount(maxUserCount);
        serverInfo.setName(name);
        serverInfo.setRequireVersion(requireVersion);
        return serverInfo;
    }

    public void increaseOnline(){
        online++;
    }

    @Override
    public String toString() {
        return "server{id:"+id+",ip:"+ip+",port:"+port+",type:"+ type +",name:"+name+"}";
    }
}
