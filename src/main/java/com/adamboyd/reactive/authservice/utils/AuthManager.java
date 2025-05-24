package com.adamboyd.reactive.authservice.utils;

import com.adamboyd.reactive.authservice.restmodels.AuthenticationRequest;
import com.adamboyd.reactive.authservice.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authservice.restmodels.BearerToken;
import com.adamboyd.reactive.authservice.services.JwtService;
import com.adamboyd.reactive.authservice.services.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.ws.rs.NotFoundException;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthManager implements ReactiveAuthenticationManager {
    private static final String LOGIN_MSG = "You've successfully logged in!";
    private static final String AUTHENTICATED_MSG = "You've successfully verified your token!";
    @NonNull
    private final JwtService jwtService;
    @NonNull
    private final UserService userService;
    @NonNull
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<Authentication> authenticate(final Authentication authentication) {
        if (!(authentication instanceof BearerToken bearerToken)) {
            return Mono.error(new IllegalArgumentException("Invalid authentication type."));
        }

        final String username = jwtService.extractUsername(bearerToken.getCredentials());

        return userService.getUserByUsername(username)
                .switchIfEmpty(Mono.error(new IllegalArgumentException(
                        String.format("Username: %s not found in Auth Manager.", username))))
                .flatMap(userDetails -> {
                    if (!jwtService.isTokenValid(bearerToken.getCredentials(), username)) {
                        return Mono.error(new IllegalArgumentException(
                                String.format("Invalid/expired token %s.",
                                        authentication.getCredentials())));
                    }
                    return Mono.just(new UsernamePasswordAuthenticationToken(
                            userDetails.getUsername(),
                            userDetails.getPassword(),
                            userDetails.getAuthorities()
                    ));
                });
    }

    public Mono<AuthenticationResponse> authenticate(final AuthenticationRequest authenticationRequest) {
        return userService.getUserByEmail(authenticationRequest.getEmail())
                .flatMap(user -> {
                    if (user == null) {
                        return Mono.error(new NotFoundException(String.format(
                                "No user found with email %s, please register via POST '/register' API.",
                                authenticationRequest.getEmail())));
                    }

                    if (!passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
                        return Mono.error(new UsernameNotFoundException("Invalid credentials."));
                    }

                    return Mono.just(AuthenticationResponse.builder()
                            .username(authenticationRequest.getUsername())
                            .token(jwtService.generateToken(user.getUsername()))
                            .message(LOGIN_MSG)
                            .build());
                })
                .onErrorResume(error -> {
                    // Log and handle errors appropriately
                    log.error("Error during login: {}", error.getMessage());
                    return Mono.error(error);
                });
    }
}
