package com.example.project_service.config;


import com.example.project_service.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
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
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizationRequest ->
                        authorizationRequest
                                .requestMatchers(
//                                        "/**"
                                        getMatchers()
                                )
                                .permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
               // .userDetailsService(authService)
               // .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               // .exceptionHandling(customizer ->
                      //  customizer
                              //  .accessDeniedHandler(accessDeniedHandler)
                                //.authenticationEntryPoint(entryPoint));
        return http.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .csrf((d)-> d.disable())
                .authorizeHttpRequests(auth -> auth
                          .requestMatchers("/swagger-resources/**", "/swagger-ui.html/**", "/v3/api-docs/**").permitAll()  // Swagger resurslariga ruxsat berish
                .anyRequest().authenticated());


        return http.build();  // Return the configured SecurityFilterChain
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
                "/swagger-resources/**",
                "/swagger-ui.html/**",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/v3/api-docs/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*");
            }
        };
    }

    private RequestMatcher[] getMatchers()
    {
        return new RequestMatcher[]///v1 v1
                {
                        //Auth
                        new AntPathRequestMatcher("/api/auth/login", "POST"),
                        //All-GET
                        new AntPathRequestMatcher("/api/**", "GET"),
                        //Application
                        new AntPathRequestMatcher("/api/application/consultation", "POST"),
                        new AntPathRequestMatcher("/api/application/question", "POST"),
                        //Telegram-bot
                        new AntPathRequestMatcher("/api/bot/**", "POST"),
                        //Counter
                        new AntPathRequestMatcher("/api/count/**", "POST"),
                        //Error
                        new AntPathRequestMatcher("/error"),
                        //Swagger
                        new AntPathRequestMatcher("/api/swagger-ui/**"),
                        new AntPathRequestMatcher("/swagger-ui/**"),
                        new AntPathRequestMatcher("/v3/api-docs/**"),
                        new AntPathRequestMatcher("/webjars/**"),
                        new AntPathRequestMatcher("/swagger-resources/**"),
                        new AntPathRequestMatcher("/configuration/**"),
                };
    }
}
