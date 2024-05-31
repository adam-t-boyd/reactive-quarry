package com.adamboyd.reactive.authservice.utils;

import com.adamboyd.reactive.authservice.repositories.UserDetailsRepository;
import com.adamboyd.reactive.authservice.restmodels.RegisterRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.ws.rs.BadRequestException;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthValidatorTest {
    @Mock
    private UserDetailsRepository userDetailsRepository;
    @InjectMocks
    private AuthValidator authValidator;
    @Test
    void newUserCredentialsAreValid_whenValid_returnTrue() {
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test","test123", "testFirstName", "testLastName");

        when(userDetailsRepository.existsByUsername(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));
        when(userDetailsRepository.existsByEmail(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));

        authValidator.newUserCredentialsAreValid(registerRequest)
                .as(StepVerifier::create)
                .expectNext(Boolean.TRUE)
                .verifyComplete();
    }

    private static Stream<Arguments> invalidRegisterRequestArgs() {
        return Stream.of(
                Arguments.of(Mono.just(true), Mono.just(false), "Email already in use."),
                Arguments.of(Mono.just(false), Mono.just(true), "Username already in use.")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidRegisterRequestArgs")
    void newUserCredentialsAreValid_whenInvalid_throwError(Mono<Boolean> isValidUsername, Mono<Boolean> isValidEmail, String errorMessage) {
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test", "test123", "testFirstName", "testLastName");

        when(userDetailsRepository.existsByUsername(any(String.class))).thenReturn(isValidUsername);
        when(userDetailsRepository.existsByEmail(any(String.class))).thenReturn(isValidEmail);


        authValidator.newUserCredentialsAreValid(registerRequest)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException &&
                        throwable.getMessage().equals(errorMessage)
                )
                .verifyLater();
    }
}