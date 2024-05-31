package com.adamboyd.reactive.repositories;

import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;

@DataR2dbcTest(useDefaultFilters = true)
class QuarryRepositoryTest {
/*
    @Autowired
    QuarryRepository quarryRepository;

    @Test
    void findAll() {
        insertQuarries();
        quarryRepository.findAll()
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
    }

    public void insertQuarries() {
        List<QuarryBO> quarryBOS = Arrays.asList(
                getQuarryBO(),
                getQuarryBO(2, true, LocalDateTime.now().plusMinutes(1)),
                getQuarryBO(3, true, LocalDateTime.now().plusMinutes(2)),
                getQuarryBO(4, false, LocalDateTime.now().plusMinutes(3)),
                getQuarryBO(5, false, LocalDateTime.now().plusMinutes(4)),
                getQuarryBO(6, false, LocalDateTime.now().plusMinutes(5))
        );
        quarryRepository.saveAll(quarryBOS).subscribe();
    }
*/
}