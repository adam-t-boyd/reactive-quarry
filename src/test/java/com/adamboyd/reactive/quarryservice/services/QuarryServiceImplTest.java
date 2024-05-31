package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.common.mappers.GenericMapper;
import com.adamboyd.reactive.quarryservice.mappers.QuarryMapper;
import com.adamboyd.reactive.quarryservice.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryservice.repositories.QuarryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static com.adamboyd.reactive.quarryservice.data.QuarryData.buildQuarry;
import static com.adamboyd.reactive.quarryservice.data.QuarryData.buildQuarryBO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuarryServiceImplTest {
    @Mock private QuarryMapper quarryMapper;
    @Mock private GenericMapper genericMapper;
   @Mock
   private QuarryRepository quarryRepository;
    @InjectMocks private QuarryServiceImpl quarryService;

 private static final QuarryBO quarryBO = buildQuarryBO();
    private static final Quarry quarry = buildQuarry();

    @Test
    void getQuarries() {
     when(quarryRepository.findAll())
             .thenReturn(Flux.just(quarryBO));
     when(quarryMapper.toQuarry(any(QuarryBO.class))).thenReturn(quarry);

     quarryService.getQuarries()
             .as(StepVerifier::create)
             .expectNext(quarry)
             .verifyComplete();
    }

    @Test
    void getQuarry() {
    }

    @Test
    void createQuarry() {
    }

    @Test
    void updateQuarry() {
    }

    @Test
    void deleteQuarry() {
    }
}