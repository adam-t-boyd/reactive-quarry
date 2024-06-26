package com.adamboyd.reactive.authservice.services;

import com.adamboyd.reactive.authservice.mappers.AuthenticationResponseMapper;
import com.adamboyd.reactive.authservice.mappers.UserDTOMapper;
import com.adamboyd.reactive.authservice.repositories.UserDetailsRepository;
import com.adamboyd.reactive.authservice.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authservice.restmodels.RegisterRequest;
import com.adamboyd.reactive.authservice.restmodels.UserDTO;
import com.adamboyd.reactive.authservice.restmodels.UserDetailsBO;
import com.adamboyd.reactive.authservice.utils.AuthValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

import static com.adamboyd.reactive.authservice.repositories.UserData.getUserDetailsBO;
import static com.adamboyd.reactive.authservice.restmodels.Role.ADMIN;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;
    @Mock
    private PasswordEncoder encoder;

    @Mock
    private AuthValidator authValidator;
    @InjectMocks
    private UserServiceImpl serviceUnderTest;
    private static final UserDetailsBO userDetailsBO = getUserDetailsBO(1, "test@gmail.com", "test", "test123", ADMIN);
    private static final UserDTO userDTO = UserDTOMapper.INSTANCE.toUserDTO(userDetailsBO);

    @Test
    void getUserByUsername() {
        final String username = "test";

        when(userDetailsRepository.findByUsername(any(String.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUserByUsername(username)
                .as(StepVerifier::create)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    void getUser_returnUsers() {
        when(userDetailsRepository.findAll()).thenReturn(Flux.just(userDetailsBO));

        serviceUnderTest.getUser()
                .as(StepVerifier::create)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    void getUser_whenSearchingByUserId_andRecordPresent_returnUser() {
        final BigDecimal userId = BigDecimal.ONE;

        when(userDetailsRepository.findById(any(BigDecimal.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUser(userId)
                .as(StepVerifier::create)
                .expectNext(userDTO)
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

        when(userDetailsRepository.findOneByEmail(any(String.class)))
                .thenReturn(Mono.just(userDetailsBO));

        serviceUnderTest.getUserByEmail(email)
                .as(StepVerifier::create)
                .expectNext(userDTO)
                .verifyComplete();
    }

    @Test
    void getUserByEmail_whenSearchingByEmail_andRecordAbsent_returnEmptyMono() {
        final String email = "test";

        when(userDetailsRepository.findOneByEmail(any(String.class)))
                .thenReturn(Mono.empty());

        serviceUnderTest.getUserByEmail(email)
                .as(StepVerifier::create)
                .verifyComplete();
    }

    @Test
    void createUserDetails_whenUserCredentialsAreValid_returnAuthenticationResponse() {
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test", "test123", "testFirstName", "testLastName");
        AuthenticationResponse authenticationResponse = AuthenticationResponseMapper.INSTANCE.toAuthenticationResponse(userDetailsBO);

       when(userDetailsRepository.save(any(UserDetailsBO.class)))
                .thenReturn(Mono.just(userDetailsBO));
        when(encoder.encode(any(String.class))).thenReturn("324732547q354354375");

        when(authValidator.newUserCredentialsAreValid(any(RegisterRequest.class))).thenReturn(Mono.just(Boolean.TRUE));

        serviceUnderTest.createUser(registerRequest)
                .as(StepVerifier::create)
                .expectNext(authenticationResponse)
                .verifyComplete();
    }

    @Test
    void createUserDetails_whenUserCredentialsAreInvalid_throwError() {
        final String errorMessage = "Email already in use.";
        final RegisterRequest registerRequest = new RegisterRequest("test@gmail.com", "test", "test123", "testFirstName", "testLastName");

        when(authValidator.newUserCredentialsAreValid(any(RegisterRequest.class)))
                .thenReturn(Mono.error(new BadRequestException(errorMessage)));

        serviceUnderTest.createUser(registerRequest)
                .as(StepVerifier::create)
                .expectErrorMatches(throwable -> throwable instanceof BadRequestException &&
                        throwable.getMessage().equals(errorMessage)
                )
                .verifyLater();
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