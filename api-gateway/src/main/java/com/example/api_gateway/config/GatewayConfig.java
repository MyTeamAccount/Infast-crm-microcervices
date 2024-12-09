package com.example.api_gateway.config;

import com.example.api_gateway.filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public GatewayConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/v1/auth/**")
                        .uri("lb://auth-service")) // Authentication routes don't require a token
                .route("crm-left-service", r -> r.path("/v1/crm/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)) // Token required
                        .uri("lb://crm-left-service"))
                .route("file-service", r -> r.path("/v1/file-storage/**")
                        .uri("lb://file-service"))
                .route("project-service", r -> r.path("/v1/project/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)) // Token required
                        .uri("lb://project-service"))
                .route("messenger-service", r -> r.path("/v1/message/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)) // Token required
                        .uri("lb://messenger-service"))
                .build();
    }
}
