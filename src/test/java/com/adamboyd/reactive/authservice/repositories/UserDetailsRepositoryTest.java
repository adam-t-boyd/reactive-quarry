package com.adamboyd.reactive.authservice.repositories;

import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

import static com.adamboyd.reactive.authservice.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.authservice.restmodels.Role.ADMIN;
import static com.adamboyd.reactive.authservice.restmodels.Role.USER;

class UserDetailsRepositoryTest {
    @InjectMocks
    UserDetailsRepository userDetailsRepository;

    @Test
    void findByEmail() {
        insertUserDetailsBOs();
        userDetailsRepository.findOneByEmail("test@gmail.com")
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

    public void insertUserDetailsBOs() {
        List<UserDetailsBO> userDetailsBOS = Arrays.asList(
                getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN),
                getUserDetailsBO(2, "test@gmail.com", "test", "test123", ADMIN),
                getUserDetailsBO(3, "test@gmail.com", "test", "test123", ADMIN),
                getUserDetailsBO(4, "test@gmail.com", "test", "test123", USER),
                getUserDetailsBO(5, "test@gmail.com", "test", "test123", USER)
        );
        userDetailsRepository.saveAll(userDetailsBOS).subscribe();
    }

}