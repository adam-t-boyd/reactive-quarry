package com.adamboyd.reactive.configuration;

import com.adamboyd.reactive.authservice.utils.AuthConverter;
import com.adamboyd.reactive.authservice.utils.AuthManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
class SecurityConfig {

  /*  @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder encoder) {
        UserDetails users = User.builder()
                .username("adam")
                .password(encoder.encode("adam"))
                .roles(Role.ADMIN.getName())
                .build();

        return new MapReactiveUserDetailsService(users);
    }*/


    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http,
                                                            AuthConverter jwtAuthConverter,
                                                            AuthManager jwtAuthManager) {
        final AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter(jwtAuthManager);
        jwtFilter.setServerAuthenticationConverter(jwtAuthConverter);
        return http
                .authorizeExchange(auth -> {
                    auth.pathMatchers("/auth/**", "/swagger-ui.html", "/v3/api-docs/**", "/webjars/**").permitAll();
                    auth.anyExchange().authenticated();
                })
                .addFilterBefore(jwtFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}