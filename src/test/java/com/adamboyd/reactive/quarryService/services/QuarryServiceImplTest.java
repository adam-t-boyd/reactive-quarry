package com.adamboyd.reactive.quarryService.services;

import com.adamboyd.reactive.authService.mappers.UserDTOMapper;
import com.adamboyd.reactive.authService.restmodels.UserDTO;
import com.adamboyd.reactive.authService.restmodels.UserDetailsBO;
import com.adamboyd.reactive.common.mappers.GenericMapper;
import com.adamboyd.reactive.quarryService.mappers.QuarryMapper;
import com.adamboyd.reactive.quarryService.mappers.QuarryMapperImpl;
import com.adamboyd.reactive.quarryService.models.businessobjects.QuarryBO;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryService.repositories.QuarryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import static com.adamboyd.reactive.authService.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.authService.restmodels.Role.ADMIN;
import static com.adamboyd.reactive.quarryService.data.QuarryData.buildQuarry;
import static com.adamboyd.reactive.quarryService.data.QuarryData.buildQuarryBO;
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