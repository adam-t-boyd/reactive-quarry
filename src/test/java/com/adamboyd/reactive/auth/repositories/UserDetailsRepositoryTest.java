package com.adamboyd.reactive.auth.repositories;

import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static com.adamboyd.reactive.auth.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.auth.restmodels.Role.ADMIN;
import static com.adamboyd.reactive.auth.restmodels.Role.USER;

class UserDetailsRepositoryTest {
    @InjectMocks
    UserDetailsRepository userDetailsRepository;

    @Test
    void findByEmail() {
        insertQuarries();
        userDetailsRepository.findByEmail("test@gmail.com")
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void findByUsername() {
    }

    @Test
    void existsByUsername() {
    }

    @Test
    void existsByEmail() {
    }

    public void insertQuarries() {
        List<UserDetailsBO> userDetailsBOS = Arrays.asList(
                getUserDetailsBO(1, "test", "test123", ADMIN),
                getUserDetailsBO(2, "test", "test123", ADMIN),
                getUserDetailsBO(3, "test", "test123", ADMIN),
                getUserDetailsBO(4, "test", "test123", USER),
                getUserDetailsBO(5, "test", "test123", USER)
        );
        userDetailsRepository.saveAll(userDetailsBOS).subscribe();
    }

}