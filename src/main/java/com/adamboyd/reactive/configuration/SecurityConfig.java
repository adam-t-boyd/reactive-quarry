package com.adamboyd.reactive.configuration;

import com.adamboyd.reactive.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.util.Optional;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final UserDetailsRepository userDetailsRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*@Order(Ordered.HIGHEST_PRECEDENCE)
    @Bean
    public SecurityWebFilterChain apiHttpSecurity(ServerHttpSecurity http) {
        http
                .securityMatcher(new PathPatternParserServerWebExchangeMatcher("/api/**"))
                .authorizeExchange((exchanges) -> exchanges
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()));
        return http.build();
    }*/

    @Bean
    public MapReactiveUserDetailsService userDetailsService(PasswordEncoder encoder) {
        //Spring is going to take care of supplying us the argument through dependency injection
        UserDetails users = User.builder()
                .username("adam")
                .password(encoder.encode("adam"))
                .roles("ADMIN")
                .build();

        return new MapReactiveUserDetailsService(users);
    }

    @Bean
    ReactiveUserDetailsService userDetailsService() {
        return username -> userDetailsRepository.findByEmail(username)
                .onErrorReturn(new UsernameNotFoundException("User not found"), Optional.empty());
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http
                                                           /* AuthenticationConverter authenticationConverter,
                                                            AuthenticationManager authenticationManager*/) {
/*
        AuthenticationWebFilter jwtFilter = new AuthenticationWebFilter();
        jwtFilter.setServerAuthenticationConverter();
*/
        return http
                .authorizeExchange(auth -> {
                    auth.anyExchange().permitAll();
                      /*  auth.pathMatchers("/login").permitAll();
                        auth.anyExchange().authenticated();*/
                })
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}