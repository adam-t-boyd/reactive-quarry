package com.adamboyd.reactive.auth;

import com.adamboyd.reactive.controllers.AuthenticationResponse;
import com.adamboyd.reactive.controllers.RegisterRequest;
import com.adamboyd.reactive.models.restwrappers.Role;
import com.adamboyd.reactive.models.restwrappers.UserDetailsEntity;
import com.adamboyd.reactive.repositories.UserDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserDetailsRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest registerRequest) {
        UserDetailsEntity user = UserDetailsEntity.builder()
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Role.USER)
                .build();

        repository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.getEmail(),
                        authenticationRequest.getPassword()));

        final Mono<Optional<User>> userDetailsEntity = repository.findByEmail(authenticationRequest.getEmail())
                .doOnError(throwable -> Mono.error(throwable));

        final String jwtToken = jwtService.generateToken(userDetailsEntity.block().get());

        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
