package com.adamboyd.reactive.services.impl;

import com.adamboyd.reactive.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.repositories.QuarryRepository;
import com.adamboyd.reactive.services.QuarryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static java.time.ZoneOffset.UTC;

@Service
@RequiredArgsConstructor
public class QuarryServiceImpl implements QuarryService {

    private final QuarryRepository quarryRepository;

    @Override
    public Flux<Quarry> getQuarries() {
        return quarryRepository.findAll()
                .map(quarryBO -> new Quarry(BigDecimal.valueOf(quarryBO.getId()), null,
                        toZonedDateTime(quarryBO.getEstablisheddate()), quarryBO.getDisused()));
    }

    @Override
    public Mono<Quarry> getQuarry(BigDecimal quarryId) {
        return quarryRepository.findById(quarryId)
                .map(quarryBO -> new Quarry(BigDecimal.valueOf(quarryBO.getId()), null,
                        toZonedDateTime(quarryBO.getEstablisheddate()), quarryBO.getDisused()));
    }

    @Override
    public Mono<Quarry> createQuarry(Quarry quarry) {
        return quarryRepository.save(QuarryBO.builder()
                        .id(quarry.quarryId().intValue())
                        .disused(quarry.disused())
                        .establisheddate(quarry.establishedDate().toLocalDateTime())
                        .build())
                .map(quarryBO -> new Quarry(BigDecimal.valueOf(quarryBO.getId()), null,
                        toZonedDateTime(quarryBO.getEstablisheddate()), quarryBO.getDisused()));
    }

    @Override
    public Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry) {
        return quarryRepository.save(QuarryBO.builder()
                        .id(quarryId.intValue())
                        .disused(quarry.disused())
                        .establisheddate(quarry.establishedDate().toLocalDateTime())
                        .build())
                .map(quarryBO -> new Quarry(BigDecimal.valueOf(quarryBO.getId()), null,
                        toZonedDateTime(quarryBO.getEstablisheddate()), quarryBO.getDisused()));
    }

    @Override
    public Mono<Void> deleteQuarry(BigDecimal quarryId) {
        return quarryRepository.deleteById(quarryId);
    }

    private ZonedDateTime toZonedDateTime(LocalDateTime localDateTime) {
        return ZonedDateTime.of(localDateTime, UTC);
    }
}
