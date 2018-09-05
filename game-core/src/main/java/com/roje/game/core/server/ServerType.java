package com.roje.game.core.server;

public enum ServerType {
    /**
     * 未知
     */
    UnKnown(0),
    /**
     * 集群服务器
     */
    Cluster(1),
    /**
     * 网关服务器
     */
    Gate(2),
    /**
     * 大厅服务器
     */
    Hall(3),
    /**
     * 游戏服务器
     */
    Game(4),
    /**
     * 游戏服务器
     */
    Chat(5),
    /**
     * 日志服务器
     */
    Log(6);
    private int type;
    ServerType(int i) {
        type = i;
    }

    public int getType() {
        return type;
    }

    public static ServerType valueOf(int i){
        if (i >= 0 && i < values().length)
            return values()[i];
        else return UnKnown;
    }
}
