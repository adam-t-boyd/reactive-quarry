package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import com.adamboyd.reactive.auth.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.auth.restmodels.RegisterRequest;
import com.adamboyd.reactive.auth.restmodels.Role;
import com.adamboyd.reactive.models.businessobjects.UserDetailsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

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
        return userDetailsRepository.save(
                        UserDetailsEntity.builder()
                                .id(registerRequest.getId())
                                .email(registerRequest.getUsername())
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
}
