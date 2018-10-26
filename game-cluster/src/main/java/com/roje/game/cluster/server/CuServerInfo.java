package com.roje.game.cluster.server;

import com.roje.game.core.server.ServerInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CuServerInfo {
    private int id;
    private String ip;
    private int port;
    private int gameId;
    private int maxUserCount;
    private String name;
    private int requireVersion;
    private int online;

    public ServerInfo toServerInfo(){
        ServerInfo info = new ServerInfo();
        info.setId(id);
        info.setName(name);
        info.setRequireVersion(requireVersion);
        info.setMaxUserCount(maxUserCount);
        info.setGameId(gameId);
        info.setIp(ip);
        info.setPort(port);
        return info;
    }

    @Override
    public String toString() {
        return "server{id:"+id+",ip:"+ip+",port:"+port+",gameId:"+gameId+",name:"+name+"}";
    }
}
