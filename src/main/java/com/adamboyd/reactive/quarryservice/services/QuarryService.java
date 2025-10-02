package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

public interface QuarryService {

    Flux<Quarry> getQuarries(final String countryCode, final Double latitude, final Double longitude);

    Mono<Quarry> getQuarry(BigDecimal quarryId);

    Mono<Quarry> createQuarry(Quarry quarry);

    Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry);

    Mono<Void> deleteQuarry(BigDecimal quarryId);

}
