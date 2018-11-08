package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "thread-config")
public class ThreadProperties {
    private IoConfig ioConfig = null;
    private SyncConfig syncConfig = null;

    private int singleThreadRoomSize = 4;

    public boolean createRoomExecutor = false;

    @Getter@Setter
    public static class IoConfig {
        private int corePoolSize = 3;
        private int maximumPoolSize = 10;
        private int keepAliveTime = 30;
        private int capacity = 100000;
    }

    @Getter@Setter
    public static class SyncConfig {
        private int commandSize = 100000;
    }
}
