package com.adamboyd.reactive.auth.services;

import com.adamboyd.reactive.auth.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
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
    public Mono<User> createUserDetails(User user) {
        return null;
    }

    @Override
    public Mono<User> updateUserDetails(BigDecimal userId, User user) {
        return null;
    }

    @Override
    public Mono<Void> deleteUserDetails(BigDecimal userId) {
        return null;
    }
}
