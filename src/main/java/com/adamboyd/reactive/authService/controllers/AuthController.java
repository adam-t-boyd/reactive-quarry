package com.adamboyd.reactive.authService.controllers;

import com.adamboyd.reactive.authService.restmodels.AuthenticationRequest;
import com.adamboyd.reactive.authService.restmodels.AuthenticationResponse;
import com.adamboyd.reactive.authService.restmodels.RegisterRequest;
import com.adamboyd.reactive.authService.services.JwtService;
import com.adamboyd.reactive.authService.services.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull
    private final UserServiceImpl userDetailsService;
    @NonNull
    private final JwtService jwtService;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        return userDetailsService
                .getUserByEmail(authenticationRequest.getEmail())
                .map(Optional::of)
                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalUser -> {
                    if (optionalUser.isEmpty()) {
                        return Mono.just(ResponseEntity.status(UNAUTHORIZED)
                                .body(String.format("No user found for %s, please Register.",
                                        authenticationRequest.getEmail())));
                    }
//                    if (encoder.matches(authenticationRequest.getPassword(), optionalUser.get().getPassword())) {
                    if (authenticationRequest.getPassword().equals(optionalUser.get().getPassword())) {
                        return Mono.just(ResponseEntity.ok().body(jwtService.generateToken(optionalUser.get().getUsername())));
                    }
                    return Mono.just(ResponseEntity.status(UNAUTHORIZED).body("Invalid Credentials."));
                });
    }

    // login works, but doesn't decrypt the db password. Needs to.
    // register needs to encode password to store in backend. needs to be available without token.
    // - need to do exception handling. throw error sthat are handled by controller advice.
    @PostMapping("/register")
    public Mono<ResponseEntity<String>> register(
            @RequestBody RegisterRequest registerRequest) {
        final Mono<AuthenticationResponse> userDetails = userDetailsService.createUser(registerRequest);

        return userDetails.map(AuthenticationResponse::getToken)
                .map(s -> ResponseEntity.ok().body(String.format("Welcome! Here's your JWT token %s", s)));
    }

    @GetMapping("/authenticate")
    public Mono<ResponseEntity<String>> auth() {
        return Mono.just(ResponseEntity.ok().body("Auth worked!"));
    }

}
