package com.roje.game.core.config;

import java.nio.ByteOrder;

public interface MessageConfig {
    /**
     * 消息头的长度(字节)
     */
    int headLen = 4;
    /**
     * 消息号的长度(字节)
     */
    int MidLen = 4;
    /**
     * uid 长度 (字节)
     */
    int UidLen = 8;
    /**
     * 最大消息长度
     */
    int maxSize = Integer.MAX_VALUE;
    /**
     * 端序
     */
    ByteOrder order = ByteOrder.LITTLE_ENDIAN;
}
