package com.adamboyd.reactive.repositories.entity;

import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.math.BigDecimal;

public interface QuarryRepository extends R2dbcRepository<QuarryEntity, BigDecimal> {
}
