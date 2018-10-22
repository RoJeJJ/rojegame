package com.roje.game.niuniu.properties;

import com.roje.game.core.config.GameProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "niuniu.config")
public class NiuNiuProperties extends GameProperties {
}
