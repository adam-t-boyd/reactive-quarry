package com.adamboyd.reactive.authService.utils;

import com.adamboyd.reactive.authService.repositories.UserDetailsRepository;
import com.adamboyd.reactive.authService.restmodels.RegisterRequest;
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

    public Mono<Boolean> newUserCredentialsAreValid(RegisterRequest registerRequest) {
        Mono<Boolean> isValidEmail = userDetailsRepository.existsByEmail(registerRequest.getUsername())
                .map(aBoolean -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        Mono.error(new BadRequestException("Email already in use."));
                    } else {
                        log.info("Passed email validation for create request.");
                    }
                    return !aBoolean;
                });

        Mono<Boolean> isValidUsername = userDetailsRepository.existsByUsername(registerRequest.getEmail())
                .map(aBoolean -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        Mono.error(new BadRequestException("Username already in use."));
                    } else {
                        log.info("Passed username validation for create request.");
                    }
                    return !aBoolean;
                });

        return Mono.zip(isValidEmail, isValidUsername, (f, s) -> f && s);
    }
}
