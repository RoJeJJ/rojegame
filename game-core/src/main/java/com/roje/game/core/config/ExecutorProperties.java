package com.roje.game.core.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
@Getter
public class ExecutorProperties {
    int threadSize = 1;
    String name;

    public void setThreadSize(int threadSize) {
        if (threadSize > 1)
            this.threadSize = threadSize;
    }

    public void setName(String name) {
        if (name != null)
            this.name = name;
    }
}
