package com.roje.game.core.thread;

public enum ThreadType {
    def,
    /**
     * 耗时线程池
     */
    io,
    /**
     * 全局同步线程
     */
    sync
}
