package com.roje.game.core.config;

import java.nio.ByteOrder;

public class MessageConfig {
    /**
     * 消息头的长度(字节)
     */
    public static int headLen = 4;
    /**
     * 消息号的长度(字节)
     */
    public static int MidLen = 4;
    /**
     * 最大消息长度
     */
    public static int maxSize = Integer.MAX_VALUE;
    /**
     * 端序
     */
    public static ByteOrder order = ByteOrder.LITTLE_ENDIAN;
}
