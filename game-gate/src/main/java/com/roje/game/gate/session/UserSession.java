package com.roje.game.gate.session;

import io.netty.channel.ChannelHandlerContext;

public class UserSession {
    private long id;
    private long rleId;
    /**
     * 游戏前端用户channel
     */
    private ChannelHandlerContext clientChannel;
    /**
     * 游戏服channel
     */
    private ChannelHandlerContext gameChannel;
    /**
     * 大厅channel
     */
    private ChannelHandlerContext hallChannel;
    private int versionCode;

    public ChannelHandlerContext clientChannel() {
        return clientChannel;
    }
    public UserSession(ChannelHandlerContext ctx){
        this.clientChannel = ctx;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public int getVersionCode() {
        return versionCode;
    }
}
