package com.proffen.configs;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtProperties {

    private String secret;
    private Long access;
    private Long refresh;

    @PostConstruct
    public void validate() {
        if (secret == null) {
            throw new IllegalArgumentException("JWT secret must not be null");
        }
        if (access == null) {
            throw new IllegalArgumentException("JWT access time must not be null");
        }
        if (refresh == null) {
            throw new IllegalArgumentException("JWT refresh time must not be null");
        }
    }
}

