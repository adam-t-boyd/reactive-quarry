package com.adamboyd.reactive.auth.controllers;

import com.adamboyd.reactive.auth.restmodels.AuthenticationRequest;
import com.adamboyd.reactive.auth.services.JwtService;
import com.adamboyd.reactive.auth.services.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Mono<User> foundUser = userDetailsService
                .findByEmail(authenticationRequest.getEmail())
                .defaultIfEmpty(null);

        if (foundUser.equals(Mono.empty())) {
            return Mono.just(ResponseEntity.status(UNAUTHORIZED)
                    .body(String.format("No user found for %s, please Register.", authenticationRequest.getEmail())));
        }

        return foundUser.flatMap(userDetails -> {
            if (encoder.matches(authenticationRequest.getPassword(), userDetails.get().getPassword())) {
                return Mono.just(ResponseEntity.ok().body(jwtService.generateToken(userDetails.get())));
            }
            return Mono.just(ResponseEntity.status(UNAUTHORIZED).body("Invalid Credentials."));
        });
    }

 /*   @PostMapping("/register")
    public Mono<ResponseEntity<AuthenticationResponse>> register(
            @RequestBody RegisterRequest registerRequest) {
        return Mono.just(ResponseEntity.ok(authenticationService.register(registerRequest)));
    }*/

    @GetMapping("/authenticate")
    public Mono<ResponseEntity<String>> auth() {
        return Mono.just(ResponseEntity.ok().body("Auth worked!"));
    }
}
