package com.angelique.todo.infrastructure.client.rest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/swagger-ui.html", "/swagger-ui/**", "/todos/**"))
                .authorizeHttpRequests(auth -> {
                    auth.anyRequest().permitAll();
                });
        return httpSecurity.build();
    }
}