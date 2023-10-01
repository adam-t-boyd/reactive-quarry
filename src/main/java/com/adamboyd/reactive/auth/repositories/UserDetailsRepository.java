package com.adamboyd.reactive.auth.repositories;

import com.adamboyd.reactive.models.businessobjects.UserDetailsEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserDetailsRepository extends R2dbcRepository<UserDetailsEntity, BigDecimal> {
    Mono<UserDetailsEntity> findByEmail(String email);

    Mono<UserDetailsEntity> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);



}
