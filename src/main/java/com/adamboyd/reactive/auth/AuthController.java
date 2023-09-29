package com.adamboyd.reactive.auth;

import com.adamboyd.reactive.controllers.AuthenticationResponse;
import com.adamboyd.reactive.controllers.RegisterRequest;
import com.adamboyd.reactive.models.restwrappers.UserDetailsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ReactiveUserDetailsService userDetailsService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody UserDetailsEntity userDetailsEntity) {
        return Mono.just("Hello " + userDetailsEntity.getEmail());
    }

    @PostMapping("/register")
    public Mono<ResponseEntity<AuthenticationResponse>> register(
            @RequestBody RegisterRequest registerRequest) {
        return Mono.just(ResponseEntity.ok(authenticationService.register(registerRequest)));
    }

    @PostMapping("/authenticate")
    public Mono<ResponseEntity<AuthenticationResponse>> register(
            @RequestBody AuthenticationRequest authenticationRequest) {
        return Mono.just(ResponseEntity.ok(authenticationService.authenticate(authenticationRequest)));
    }
}
