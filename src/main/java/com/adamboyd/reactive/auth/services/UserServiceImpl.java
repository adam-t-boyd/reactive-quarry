package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import com.adamboyd.reactive.auth.restmodels.Role;
import com.adamboyd.reactive.models.businessobjects.UserDetailsBO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder encoder;
    private final UserDetailsRepository userDetailsRepository;

    public Mono<User> getUserByUsername(String username) {
        return userDetailsRepository.findByUsername(username)
                .map(userDetailsBO -> new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(),
                        userDetailsBO.getAuthorities()));
    }

    @Override
    public Flux<User> getUser() {
        return userDetailsRepository.findAll()
                .map(userDetailsBO -> new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(),
                        userDetailsBO.getAuthorities()));
    }

    @Override
    public Mono<User> getUser(BigDecimal userId) {
        return userDetailsRepository.findById(userId)
                .map(userDetailsBO -> new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(),
                        userDetailsBO.getAuthorities()));
    }

    public Mono<User> getUserByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .map(userDetailsBO -> new User(userDetailsBO.getUsername(),
                        userDetailsBO.getPassword(),
                        userDetailsBO.getAuthorities()));
    }

    @Override
    public Mono<AuthenticationResponse> createUser(RegisterRequest registerRequest) {
        areValidUserCredentials(registerRequest);
        return Mono.just(registerRequest)
                .filterWhen(this::areValidUserCredentials)
                .flatMap(regReq -> userDetailsRepository.save(
                        UserDetailsBO.builder()
                                .id(null)
                                .email(regReq.getEmail())
                                .username(regReq.getUsername())
                                .password(encoder.encode(regReq.getPassword()))
                                .role(Role.ADMIN)
                                .firstname(regReq.getFirstname())
                                .lastname(regReq.getLastname())
                                .build()))
                .map(userDetailsBO -> new AuthenticationResponse(
                        userDetailsBO.getUsername(),
                        userDetailsBO.getPassword()));
    }

    @Override
    public Mono<User> updateUser(BigDecimal userId, RegisterRequest updateRequest) {
        return null;
    }

    @Override
    public Mono<Void> deleteUser(BigDecimal userId) {
        return null;
    }

    public Mono<Boolean> areValidUserCredentials(RegisterRequest registerRequest) {
        Mono<Boolean> isInvalidUsername = userDetailsRepository.existsByUsername(registerRequest.getUsername())
                .map(aBoolean -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        Mono.error(new BadRequestException("Email already in use."));
                    } else {
                        log.info("Passed email validation for create request.");
                    }
                    return aBoolean;
                });

        Mono<Boolean> isInvalidEmail = userDetailsRepository.existsByEmail(registerRequest.getEmail())
                .map(aBoolean -> {
                    if (Boolean.TRUE.equals(aBoolean)) {
                        Mono.error(new BadRequestException("Username already in use."));
                    } else {
                        log.info("Passed username validation for create request.");
                    }
                    return aBoolean;
                });

        return Mono.zip(isInvalidEmail, isInvalidUsername, (f, s) -> f && s);
    }
}
