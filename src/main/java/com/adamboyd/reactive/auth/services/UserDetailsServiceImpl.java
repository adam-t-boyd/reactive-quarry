package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import com.adamboyd.reactive.auth.restmodels.Role;
import com.adamboyd.reactive.models.businessobjects.UserDetailsEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.ws.rs.BadRequestException;
import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final PasswordEncoder encoder;
    private final UserDetailsRepository userDetailsRepository;

    public Mono<UserDetails> getUserDetailsByUsername(String username) {
        return userDetailsRepository.findByUsername(username)
                .map(userDetailsEntity -> new User(userDetailsEntity.getUsername(),
                        userDetailsEntity.getPassword(),
                        userDetailsEntity.getAuthorities()));
    }

    @Override
    public Flux<User> getUserDetails() {
        return userDetailsRepository.findAll()
                .map(userDetailsEntity -> new User(userDetailsEntity.getUsername(),
                        userDetailsEntity.getPassword(),
                        userDetailsEntity.getAuthorities()));
    }

    @Override
    public Mono<User> getUserDetails(BigDecimal userId) {
        return null;
    }

    public Mono<User> findByEmail(String email) {
        return userDetailsRepository.findByEmail(email)
                .map(userDetailsEntity -> new User(userDetailsEntity.getUsername(),
                        userDetailsEntity.getPassword(),
                        userDetailsEntity.getAuthorities()));
    }

    @Override
    public Mono<AuthenticationResponse> createUserDetails(RegisterRequest registerRequest) {
        validateCreateRequest(registerRequest);
        return userDetailsRepository.save(
                        UserDetailsEntity.builder()
                                .id(null)
                                .email(registerRequest.getEmail())
                                .username(registerRequest.getUsername())
                                .password(encoder.encode(registerRequest.getPassword()))
                                .role(Role.ADMIN)
                                .firstname(registerRequest.getFirstname())
                                .lastname(registerRequest.getLastname())
                                .build())
                .map(userDetailsEntity -> new AuthenticationResponse(
                        userDetailsEntity.getUsername(),
                        userDetailsEntity.getPassword()
                ));
    }

    @Override
    public Mono<User> updateUserDetails(BigDecimal userId, RegisterRequest updateRequest) {
        return null;
    }

    @Override
    public Mono<Void> deleteUserDetails(BigDecimal userId) {
        return null;
    }

    public void validateCreateRequest(RegisterRequest registerRequest) {
        Mono<Boolean> isValidUsername = userDetailsRepository.existsByUsername(registerRequest.getUsername());
        Mono<Boolean> isValidEmail = userDetailsRepository.existsByEmail(registerRequest.getEmail());

        isValidEmail.map(aBoolean -> {
            if (aBoolean.equals(true)) {
                Mono.error(new BadRequestException("Email already in use."));
            } else {
                log.info("Passed email validation for create request.");
            }
            return aBoolean;
        });

        isValidUsername.map(aBoolean -> {
            if (aBoolean.equals(true)) {
                Mono.error(new BadRequestException("Username already in use."));
            } else {
                log.info("Passed username validation for create request.");
            }
            return aBoolean;
        });
    }
}
