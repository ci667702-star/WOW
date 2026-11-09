package com.example.JavaProject.global.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/** application.yaml의 app.cors.* 설정 */
@ConfigurationProperties(prefix = "app.cors")
public record CorsProperties(List<String> allowedOrigins) {
    public CorsProperties {
        allowedOrigins = allowedOrigins == null ? List.of() : List.copyOf(allowedOrigins);
    }
}
