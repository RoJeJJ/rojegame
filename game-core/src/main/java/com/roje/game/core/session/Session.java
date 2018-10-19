package com.roje.game.core.session;


import com.roje.game.message.frame.Frame;
import io.netty.channel.Channel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Session {
    protected String id;
    @Getter @Setter
    protected long uid;
    protected Channel channel;
    public Session(Channel channel){
        this.channel = channel;
        id = channel.id().asShortText();
    }

    public String id(){
        return id;
    }

    public Channel channel(){
        return channel;
    }

    public void send(Frame frame){
        if (channel != null && channel.isActive())
            channel.writeAndFlush(frame);
        else
            log.warn("{}发送消息失败",uid);
    }

//    public void send(int mid,byte[] bytes){
//        if (channel != null && channel.isActive()){
//            ByteBuf buf = Unpooled.buffer(MessageConfig.MidLen+MessageConfig.UidLen+bytes.length);
//            buf.writeInt(mid);
//            buf.writeLong(uid);
//            buf.writeBytes(bytes);
//            channel.writeAndFlush(buf);
//        }else
//            log.warn("{}发送消息失败",uid);
//    }
    public void onClosed(){}

    public void onOpen(){}

    public void close() {
        if (channel != null)
            channel.close();
    }

    public void open() {
        onOpen();
    }
}
