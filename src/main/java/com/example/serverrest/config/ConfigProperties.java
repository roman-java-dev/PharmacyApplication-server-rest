package com.example.serverrest.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConstructorBinding
@ConfigurationProperties(prefix = "config")
public record ConfigProperties (
        String host,
        int port,
        ServiceProperties service) {
}
