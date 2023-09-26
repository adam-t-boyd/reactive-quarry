package com.adamboyd.reactive.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http/*.cors(ServerHttpSecurity.CorsSpec::disable)*/
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
//                .authorizeExchange(auth -> auth.pathMatchers("/quarry/**").permitAll()
//                );
        return http.build();
    }

}