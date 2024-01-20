package com.example.dbsservice.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "tpb.token")
@Data
public class TokenConfig {
    private String secret;
    private String expriedTime;
}
