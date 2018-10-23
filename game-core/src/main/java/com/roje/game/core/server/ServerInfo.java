package com.roje.game.core.server;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

@Getter
@Setter
@ConfigurationProperties("server-info")
public class ServerInfo implements Serializable {
    private static final long serialVersionUID = -4979058100694820452L;
    private int id;
    protected String ip;
    protected int port;
    protected int gameId;
    protected int maxUserCount;
    protected String name;
    protected int requireVersion;

    public JsonObject json(){
        JsonObject data = new JsonObject();
        data.addProperty("ip",ip);
        data.addProperty("port",port);
        data.addProperty("gameId",gameId);
        data.addProperty("max_user_count",maxUserCount);
        data.addProperty("name",name);
        data.addProperty("required_version",requireVersion);
        return data;
    }

    @Override
    public String toString() {
        return "server{id:"+id+",ip:"+ip+",port:"+port+",gameId:"+gameId+",name:"+name+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerInfo){
            ServerInfo info = (ServerInfo) obj;
            return ip.equals(info.getIp()) && port == info.getPort() && gameId == info.getGameId();
        }
        return false;
    }
}
