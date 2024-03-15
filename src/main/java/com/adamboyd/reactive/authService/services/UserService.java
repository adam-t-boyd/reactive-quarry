package com.adamboyd.reactive.authService.services;

import com.adamboyd.reactive.authService.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authService.restmodels.RegisterRequest;
import com.adamboyd.reactive.authService.restmodels.UserDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserService {

    Mono<UserDTO> getUserByUsername(String username);

    Flux<UserDTO> getUser();

    Mono<UserDTO> getUser(BigDecimal userId);

    Mono<AuthenticationResponse> createUser(RegisterRequest user);

    Mono<UserDTO> updateUser(BigDecimal userId, RegisterRequest user);

    Mono<Void> deleteUser(BigDecimal userId, RegisterRequest user);

}
