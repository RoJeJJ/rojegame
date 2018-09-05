package com.roje.game.core.util;

import java.time.Clock;

public class TimeUtil {
    /**
     * 服务器时间偏移量
     */
    private static long timeOffset = 0;//86400000

    /**
     * @return 获取当前纪元毫秒 1970-01-01T00:00:00Z.
     */
    public static long currentTimeMillis() {
        return Clock.systemDefaultZone().instant().toEpochMilli() + timeOffset;
    }

    /**
     * @return 获取当前纪元秒 1970-01-01T00:00:00Z.
     */
    public static long epochSecond() {
        return Clock.systemDefaultZone().instant().getEpochSecond() + timeOffset / 1000;
    }
}
