package com.roje.game.core.entity.role;

import io.netty.channel.Channel;

public class Role {

    private final long id;

    private final String account;

    private String nickname;

    private String avatar;

    private long card;

    private long gold;

    private Channel channel;

    public Role(long id, String account, String nickname, String avatar, long card, long gold){
        this.id = id;
        this.account = account;
        this.nickname = nickname;
        this.avatar = avatar;
        this.card = card;
        this.gold = gold;
    }

    public long id() {
        return id;
    }

    public String account() {
        return account;
    }

    public String nickname() {
        return nickname;
    }

    public String avatar() {
        return avatar;
    }

    public long getCard() {
        return card;
    }

    public long getGold() {
        return gold;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }
}
