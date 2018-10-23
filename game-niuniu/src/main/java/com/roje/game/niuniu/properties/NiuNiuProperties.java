package com.roje.game.niuniu.properties;

import com.roje.game.core.config.GameProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "niuniu")
public class NiuNiuProperties extends GameProperties {
}
