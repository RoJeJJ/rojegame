package com.roje.game.core.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;


public class MessageUtil {
    /**
     * 小端字节转int
     * @param bytes 字节数组
     * @param offset 偏移
     * @param len 长度
     * @return int数据
     */
    public static int getMid(byte[] bytes, int offset, int len){
        ByteBuf buf = Unpooled.buffer(4);
        buf.writeBytes(bytes,offset,len);
        return buf.readInt();
    }
    public static byte[] getMessage(byte[] bytes,int offset,int len){
        byte[] des = new byte[len];
        System.arraycopy(bytes,offset,des,0,len);
        return des;
    }

}
