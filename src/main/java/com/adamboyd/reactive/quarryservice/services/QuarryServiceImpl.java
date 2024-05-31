package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.quarryservice.mappers.QuarryMapper;
import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryservice.repositories.QuarryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class QuarryServiceImpl implements QuarryService {
    private final QuarryRepository quarryRepository;
    private static final QuarryMapper quarryMapper = QuarryMapper.INSTANCE;
    @Override
    public Flux<Quarry> getQuarries() {
        return quarryRepository
                .findAll()
                .map(quarryMapper::toQuarry);
    }

    @Override
    public Mono<Quarry> getQuarry(BigDecimal quarryId) {
        return quarryRepository
                .findById(quarryId)
                .map(quarryMapper::toQuarry);
    }

    @Override
    public Mono<Quarry> createQuarry(Quarry quarry) {
        return quarryRepository.save(quarryMapper.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry) {
        return quarryRepository.save(quarryMapper.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Void> deleteQuarry(BigDecimal quarryId) {
        return quarryRepository.deleteById(quarryId);
    }
}
