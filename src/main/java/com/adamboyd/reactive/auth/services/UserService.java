package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserService {

    Mono<User> getUserByUsername(String username);

    Flux<User> getUser();

    Mono<User> getUser(BigDecimal userId);

    Mono<AuthenticationResponse> createUser(RegisterRequest user);

    Mono<User> updateUser(BigDecimal userId, RegisterRequest user);

    Mono<Void> deleteUser(BigDecimal userId);

}
