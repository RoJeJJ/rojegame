package com.roje.game.core.server;

import com.google.gson.JsonElement;
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
    protected int type;
    protected int maxUserCount;
    protected String name;
    protected int requireVersion;

    public JsonElement json(){
        JsonObject object = new JsonObject();
        object.addProperty("id",id);
        object.addProperty("ip",ip);
        object.addProperty("port",port);
        object.addProperty("type", type);
        object.addProperty("name",name);
        return object;
    }

    @Override
    public String toString() {
        return "server{id:"+id+",ip:"+ip+",port:"+port+",type:"+ type +",name:"+name+"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServerInfo){
            ServerInfo info = (ServerInfo) obj;
            return ip.equals(info.getIp()) && port == info.getPort() && type == info.getType();
        }
        return false;
    }
}
