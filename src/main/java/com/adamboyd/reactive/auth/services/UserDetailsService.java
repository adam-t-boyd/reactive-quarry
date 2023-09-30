package com.adamboyd.reactive.auth.services;

import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserDetailsService {
    Flux<User> getUserDetails();

    Mono<User> getUserDetails(BigDecimal userId);

    Mono<User> createUserDetails(User user);

    Mono<User> updateUserDetails(BigDecimal userId, User user);

    Mono<Void> deleteUserDetails(BigDecimal userId);

}
