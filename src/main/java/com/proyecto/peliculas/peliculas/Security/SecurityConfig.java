package com.proyecto.peliculas.peliculas.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request -> request
                .requestMatchers(
                        "/static/**",
                        "/index.html",
                        "/",
                        "/*.css",
                        "/*.js",
                        "/css/**",
                        "/js/**",
                        "/images/**",
                        "/favicon.ico")
                .permitAll()
                .requestMatchers("/api/movies/**", "/api/mangas", "/api/mangas/**", "/api/movies/bulk").permitAll())
                .csrf(csrf -> csrf.disable());
        return http.build();
    }

}
