package com.roje.game.cluster.server;

import com.google.gson.JsonObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServerInfo {
    private String ip;
    private int port;
    private int gameId;
    private int maxUserCount;
    private String name;
    private int requireVersion;
    private int online;

    public JsonObject json(){
        JsonObject data = new JsonObject();
        data.addProperty("ip",ip);
        data.addProperty("port",port);
        data.addProperty("online",online);
        data.addProperty("gameId",gameId);
        data.addProperty("max_user_count",maxUserCount);
        data.addProperty("name",name);
        data.addProperty("required_version",requireVersion);
        return data;
    }
}
