package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserDetailsService {
    Flux<User> getUserDetails();

    Mono<User> getUserDetails(BigDecimal userId);

    Mono<AuthenticationResponse> createUserDetails(RegisterRequest user);

    Mono<User> updateUserDetails(BigDecimal userId, RegisterRequest user);

    Mono<Void> deleteUserDetails(BigDecimal userId);

}
