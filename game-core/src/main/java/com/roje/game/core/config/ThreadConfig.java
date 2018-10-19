package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "thread-config")
public class ThreadConfig {
    private int ioCorePoolSize = 3;
    private int ioMaximumPoolSize = 10;
    private int ioKeepAliveTime = 30;
    private int ioCapacity = 100000;
    private String syncName = "sync";
    private int syncCommandSize = 100000;
}
