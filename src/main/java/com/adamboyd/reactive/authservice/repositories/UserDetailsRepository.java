package com.adamboyd.reactive.authservice.repositories;

import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface UserDetailsRepository extends R2dbcRepository<UserDetailsBO, BigDecimal> {
    Mono<UserDetailsBO> findOneByEmail(String email);

    Mono<UserDetailsBO> findByUsername(String username);

    Mono<Boolean> existsByUsername(String username);

    Mono<Boolean> existsByEmail(String email);


}
