package com.roje.game.core.util;

public class ServerUtil {
    private static final int MB = 1024*1024;

    public static int freeMemory(){
        return (int) Runtime.getRuntime().freeMemory()/MB;
    }
    public static int totalMemory(){
        return (int) (Runtime.getRuntime().totalMemory()/MB);
    }
}
