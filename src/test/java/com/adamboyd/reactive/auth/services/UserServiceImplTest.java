package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;
import java.util.stream.Stream;

import static com.adamboyd.reactive.auth.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.auth.restmodels.Role.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private PasswordEncoder encoder;
    @InjectMocks
    private UserServiceImpl serviceUnderTest;

    private static Stream<Arguments> invalidRegisterRequestArgs() {
        return Stream.of(
                Arguments.of(Mono.just(true), Mono.just(false), "Email already in use."),
                Arguments.of(Mono.just(false), Mono.just(true), "Username already in use.")
        );
    }

    @Test
    void getUserByUsername() {
        final String username = "test";
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);

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
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);

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
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUser(userId)
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void getUser_whenSearchingByUserId_andRecordAbsent_returnEmptyMono() {
        final BigDecimal userId = BigDecimal.ONE;

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.empty());

        serviceUnderTest.getUser(userId)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void getUserByEmail_whenSearchingByEmail_andRecordPresent_returnUserMono() {
        final String email = "test";
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);

        when(userDetailsRepository.findByEmail(any(String.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUserByEmail(email)
                .as(StepVerifier::create)
                .expectNext(new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(), userDetailsBO.getAuthorities()))
                .verifyComplete();
    }

    @Test
    void getUserByEmail_whenSearchingByEmail_andRecordAbsent_returnEmptyMono() {
        final String email = "test";

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.empty());

        serviceUnderTest.getUserByEmail(email)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void createUserDetails_whenUserCredentialsAreValid_returnUserDetailsBO() {
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test", "test123", "testFirstName", "testLastName");
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);

        when(userDetailsRepository.save(any(UserDetailsBO.class)))
                .thenReturn(Mono.just(userDetailsBO));
        when(encoder.encode(any(String.class))).thenReturn("324732547q354354375");

        when(userDetailsRepository.existsByUsername(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));
        when(userDetailsRepository.existsByEmail(any(String.class))).thenReturn(Mono.just(Boolean.FALSE));

        serviceUnderTest.createUser(registerRequest)
                .as(StepVerifier::create)
                .expectNext(new AuthenticationResponse(
                        userDetailsBO.getUsername(),
                        userDetailsBO.getPassword()))
                .verifyComplete();
    }

    @ParameterizedTest
    @MethodSource("invalidRegisterRequestArgs")
    void createUserDetails_whenUserCredentialsAreValid_returnError(Mono<Boolean> isValidUsername, Mono<Boolean> isValidEmail, String errorMessage) {
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test", "test123", "testFirstName", "testLastName");
        final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "encodedTest123", ADMIN);

        when(userDetailsRepository.save(any(UserDetailsBO.class)))
                .thenReturn(Mono.just(userDetailsBO));
        when(encoder.encode(any(String.class))).thenReturn("encodedTest123");
        when(userDetailsRepository.existsByUsername(any(String.class))).thenReturn(isValidUsername);
        when(userDetailsRepository.existsByEmail(any(String.class))).thenReturn(isValidEmail);

        serviceUnderTest.createUser(registerRequest)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException &&
                        throwable.getMessage().equals(errorMessage))
                .verify();
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