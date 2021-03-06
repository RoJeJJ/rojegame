package com.roje.game.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter@Setter
@ConfigurationProperties(prefix = "room")
public class RoomProperties {
    protected int userMaxCreateRoomCount;
    protected int maxRoomSize;
    protected int roomMaxRole;
}
