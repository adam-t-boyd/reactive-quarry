package com.adamboyd.reactive.configuration;

import com.adamboyd.reactive.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
class SecurityConfig {

    private final UserDetailsRepository userDetailsRepository;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

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

    // encodes password, fetches user details
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager reactiveAuthenticationManager(AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return username -> userDetailsRepository.findByEmail(username)
                .doOnError(throwable -> Mono.error(new UsernameNotFoundException("User not found")));
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
                    auth.pathMatchers("/api/v1/auth/**").permitAll();
                    auth.anyExchange().authenticated();
                })
                .addFilterBefore(jwtAuthFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .build();
    }
}