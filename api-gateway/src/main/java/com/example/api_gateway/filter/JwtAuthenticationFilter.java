package com.example.api_gateway.filter;

import com.example.api_gateway.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.Predicate;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        // List of endpoints that don't require token validation
        final List<String> openApiEndpoints = List.of("/v1/auth/login", "/v1/auth/register");

        // Predicate to check if the request is for a secured endpoint
        Predicate<ServerHttpRequest> isSecured = r -> openApiEndpoints.stream()
                .noneMatch(uri -> r.getURI().getPath().contains(uri));

        if (isSecured.test(request)) {
            // Check for Authorization header
            if (!request.getHeaders().containsKey("Authorization")) {
                return onError(exchange, "Authorization header missing", HttpStatus.UNAUTHORIZED);
            }

            // Extract token from Authorization header
            String token = request.getHeaders().getFirst("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            } else {
                return onError(exchange, "Invalid Authorization header format", HttpStatus.UNAUTHORIZED);
            }

            // Validate token
            try {
                jwtUtil.validateToken(token); // Throws exception if token is invalid
                // Extract claims and add them as headers (optional)
                String username = jwtUtil.extractUsername(token);
                exchange.getRequest().mutate()
                        .header("X-Authenticated-Username", username)
                        .build();
            } catch (Exception e) {
                return onError(exchange, "Invalid or expired token", HttpStatus.UNAUTHORIZED);
            }
        }

        return chain.filter(exchange);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
