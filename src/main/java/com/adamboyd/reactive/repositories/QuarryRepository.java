package com.adamboyd.reactive.repositories;

import com.adamboyd.reactive.models.businessobjects.QuarryBO;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

import java.math.BigDecimal;

public interface QuarryRepository extends R2dbcRepository<QuarryBO, BigDecimal> {
}
