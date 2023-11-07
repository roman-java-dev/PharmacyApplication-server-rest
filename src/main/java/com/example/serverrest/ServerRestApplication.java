package com.example.serverrest;

import com.example.serverrest.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@ConfigurationPropertiesScan("com.example.serverrest.config")
public class ServerRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerRestApplication.class, args);
    }
}
