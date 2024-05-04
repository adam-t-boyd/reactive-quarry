package com.adamboyd.reactive.authservice.controllers;

import com.adamboyd.reactive.authservice.restmodels.*;
import com.adamboyd.reactive.authservice.services.JwtService;
import com.adamboyd.reactive.authservice.services.UserServiceImpl;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @NonNull private final UserServiceImpl userDetailsService;
    @NonNull private final JwtService jwtService;
    @NonNull private final PasswordEncoder passwordEncoder;
    private static final String WELCOME_TOKEN_MSG = "Welcome! Here's your JWT token %s";

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Mono<UserDTO> userByEmail = userDetailsService.getUserByEmail(authenticationRequest.getEmail());
        return userByEmail
                .map(Optional::ofNullable)
//                .defaultIfEmpty(Optional.empty())
                .flatMap(optionalUser -> {
                    if (optionalUser.isEmpty()) {
                        return Mono.just(ResponseEntity.status(UNAUTHORIZED)
                                .body(String.format("No user found for %s, please register via '/register'.",
                                        authenticationRequest.getEmail())));
                    }
                   else if (passwordEncoder.matches(authenticationRequest.getPassword(), optionalUser.get().getPassword())) {
                        return Mono.just(ResponseEntity.ok().body(jwtService.generateToken(optionalUser.get().getUsername())));
                    }
                    return Mono.just(ResponseEntity.status(UNAUTHORIZED).body("Invalid Credentials."));
                });
    }

    // login works, but doesn't decrypt the db password. Needs to.
    // register needs to encode password to store in backend. needs to be available without token.
    // - need to do exception handling. throw error that are handled by controller advice.
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> register(
            @RequestBody RegisterRequest registerRequest) {
        final Mono<AuthenticationResponse> userDetails = userDetailsService.createUser(registerRequest);
        return userDetails
                .map(authenticationResponse ->
                    String.format(WELCOME_TOKEN_MSG, authenticationResponse.getToken()));
    }

    @GetMapping("/authenticate")
    public Mono<ResponseEntity<String>> auth(@RequestBody AuthenticationResponse authenticationResponse) {
        // WIP
        boolean isTokenValid = jwtService.isTokenValid(authenticationResponse.getToken(), UserDetailsBO.builder().build());
        if (isTokenValid) {
            Mono.just(ResponseEntity.ok().body("Auth worked!"));
        }
        return Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or token."));
    }

}
