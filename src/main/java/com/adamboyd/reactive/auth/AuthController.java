package com.adamboyd.reactive.auth;

import com.adamboyd.reactive.auth.restmodels.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final ReactiveUserDetailsService users;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    @PostMapping("/login")
    public Mono<ResponseEntity<String>> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Mono<UserDetails> foundUser = users
                .findByUsername(authenticationRequest.getEmail())
                .defaultIfEmpty(null);

        if (foundUser.equals(Mono.empty())) {
            return Mono.just(ResponseEntity.status(UNAUTHORIZED)
                    .body(String.format("No user found for %s, please Register.", authenticationRequest.getEmail())));
        }

        return foundUser.flatMap(userDetails -> {
            if (encoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
                return Mono.just(ResponseEntity.ok().body(jwtService.generateToken(userDetails)));
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
