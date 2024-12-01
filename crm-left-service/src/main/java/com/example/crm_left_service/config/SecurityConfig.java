package com.example.crm_left_service.config;

import com.example.crm_left_service.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRFni o'chirish
//                .cors(cors -> cors.disable())  // CORSni o'chirish
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)  // JWT filterini qo'shish
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-resources/**", "/swagger-ui.html/**", "/v3/api-docs/**").permitAll()  // Swagger resurslariga ruxsat berish
                        .anyRequest().authenticated()  // Boshqa barcha so'rovlar uchun autentifikatsiya talab qilish
                );

        return http.build();  // Yangi Spring Security konfiguratsiya shakli
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-resources/**",
                "/swagger-ui.html/**",
                "/v3/api-docs/**",
                "/swagger-ui/**",
                "/v3/api-docs/**"
        );
    }

    // CORS konfiguratsiyasini yaxshilash
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Faqat ruxsat berilgan metodlar
                        .allowedOrigins("*");  // Barcha originlarga ruxsat berish (bu xavfsizlikni oshirish uchun qattiqlashtirilishi mumkin)
            }
        };
    }
}
