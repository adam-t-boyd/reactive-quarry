package com.adamboyd.reactive.authservice.utils;

import com.adamboyd.reactive.authservice.repositories.UserDetailsRepository;
import com.adamboyd.reactive.authservice.restmodels.RegisterRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.ws.rs.BadRequestException;

@Component
@Slf4j
@RequiredArgsConstructor
public class AuthValidator {
    private final UserDetailsRepository userDetailsRepository;

    public Mono<Boolean> newUserCredentialsAreValid(final RegisterRequest registerRequest) {
        final Mono<Boolean> isValidEmail = userDetailsRepository.existsByEmail(registerRequest.getEmail())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BadRequestException("Email already in use."));
                    }
                    log.info("{} passed email validation for create request.", registerRequest.getEmail());
                    return Mono.just(true);
                });

        final Mono<Boolean> isValidUsername = userDetailsRepository.existsByUsername(registerRequest.getUsername())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new BadRequestException("Username already in use."));
                    }
                    log.info("{} passed username validation for create request.", registerRequest.getUsername());
                    return Mono.just(true);
                });

        return Mono.zip(isValidEmail, isValidUsername)
                .map(tuple -> tuple.getT1() && tuple.getT2());
    }
}
