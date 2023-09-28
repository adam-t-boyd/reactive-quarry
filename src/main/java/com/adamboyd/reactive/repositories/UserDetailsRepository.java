package com.adamboyd.reactive.repositories;

import com.adamboyd.reactive.models.businessobjects.QuarryEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDetailsRepository extends R2dbcRepository<QuarryEntity, BigDecimal> {
    Mono<Optional<User>> findByEmail(String email);
}
