package com.roje.game.core.session;


import com.google.protobuf.Message;
import com.roje.game.core.config.MessageConfig;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Session {
    private static final Logger LOG = LoggerFactory.getLogger(Session.class);
    private String id;
    protected long uid;
    protected long rid;
    protected Channel channel;
    private int versionCode;
    public Session(Channel channel){
        this.channel = channel;
        id = channel.id().asShortText();
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String id(){
        return id;
    }

    public Channel channel(){
        return channel;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getRid() {
        return rid;
    }

    public void setRid(long rid) {
        this.rid = rid;
    }

    public abstract void sessionClosed();

    public void send(Message message){
        if (channel != null && channel.isActive())
            channel.writeAndFlush(message);
        else
            LOG.warn("{}发送消息失败",uid);
    }

    public void send(int mid,byte[] bytes){
        if (channel != null && channel.isActive()){
            ByteBuf buf = Unpooled.buffer(MessageConfig.MidLen+MessageConfig.UidLen+bytes.length);
            buf.writeInt(mid);
            buf.writeLong(uid);
            buf.writeBytes(bytes);
            channel.writeAndFlush(buf);
        }else
            LOG.warn("{}发送消息失败",uid);
    }
}
