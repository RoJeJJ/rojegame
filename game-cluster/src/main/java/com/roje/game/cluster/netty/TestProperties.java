package com.roje.game.cluster.netty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "game.cluster")
public class TestProperties {

    private int id;
    private String name;
    private boolean ok;
    private Type type;
    public enum Type{
        one,two
    }
}
