package com.surest.member.integration;

import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
public class NoSecurityConfig {


    @Bean
    @Order(1)
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {

        http
                .securityMatcher("/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}

