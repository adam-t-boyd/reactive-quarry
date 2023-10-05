package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;

import static com.adamboyd.reactive.auth.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.auth.restmodels.Role.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserServiceImpl serviceUnderTest;

    @Test
    void getUserByUsername() {
        final String username = "test";
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test", "test123", ADMIN);

        when(userDetailsRepository.findByUsername(any(String.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUserByUsername(username)
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void getUser_returnUsers() {
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test", "test123", ADMIN);

        when(userDetailsRepository.findAll()).thenReturn(Flux.just(userDetailsBO));

        serviceUnderTest.getUser()
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void getUser_whenSearchingByUserId_andRecordPresent_returnUser() {
        final BigDecimal userId = BigDecimal.ONE;
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test", "test123", ADMIN);

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUser(userId)
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void getUser_whenSearchingByUserId_andRecordAbsent_returnEmptyFlux() {
        final BigDecimal userId = BigDecimal.ONE;

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.empty());

        serviceUnderTest.getUser(userId)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void findByEmail_whenSearchingByEmail_andRecordPresent_returnUserMono() {
        final String email = "test";
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test", "test123", ADMIN);

        when(userDetailsRepository.findByEmail(any(String.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUserByEmail(email)
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void createUserDetails() {
    }

    @Test
    void updateUserDetails() {
    }

    @Test
    void deleteUserDetails() {
    }

    @Test
    void areValidUserCredentials() {
    }
}