package com.adamboyd.reactive.services;

import com.adamboyd.reactive.models.restmodels.rocks.Quarry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface QuarryService {

    Flux<Quarry> getQuarries();

    Mono<Quarry> getQuarry(BigDecimal quarryId);

    Mono<Quarry> createQuarry(Quarry quarry);

    Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry);

    Mono<Void> deleteQuarry(BigDecimal quarryId);

}
