package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ThreadConfig {
    private int ioCorePoolSize;
    private int ioMaximumPoolSize;
    private int ioKeepAliveTime;
    private int ioCapacity;
    private String syncName;
    private long syncTimeInterval;
    private int syncCommandSize;
}
