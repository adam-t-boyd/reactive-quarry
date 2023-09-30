package com.adamboyd.reactive.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class AuthManager implements ReactiveAuthenticationManager {
    private final JwtService jwtService;
    private final ReactiveUserDetailsService users;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(
                        authentication
                )
                .cast(BearerToken.class)
                .flatMap(bearerToken -> {
                    final String username = jwtService.extractUsername(bearerToken.getCredentials());
                    final Mono<UserDetails> foundUser = users.findByUsername(username).defaultIfEmpty(null);
                    if (foundUser.equals(Mono.empty())) {
                        Mono.error(new IllegalArgumentException(String.format(
                                "Username: %s not found in Auth Manager.", username)));
                    }

                    return foundUser.flatMap(
                            userDetails -> {
                                if (jwtService.isTokenValid(bearerToken.getCredentials(), userDetails)) {
                                    return Mono.justOrEmpty(new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
                                            userDetails.getPassword(), userDetails.getAuthorities()));
                                }
                                return Mono.error(new IllegalArgumentException("Invalid/expired token (Auth Manager)."));
                            });
                });
    }
}
