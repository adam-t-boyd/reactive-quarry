package com.adamboyd.reactive.authService.repositories;

import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserDetailsRepository extends R2dbcRepository<UserDetailsBO, BigDecimal> {
    Mono<UserDetailsBO> findByEmail(String email);

    Mono<UserDetailsBO> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);



}
