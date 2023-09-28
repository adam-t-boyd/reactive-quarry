package com.adamboyd.reactive.controllers;

import com.adamboyd.reactive.models.restwrappers.UserDetailsEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping()
@RequiredArgsConstructor
public class AuthController {

    final ReactiveUserDetailsService userDetailsService;

    @PostMapping("/login")
    public Mono<String> login(@RequestBody UserDetailsEntity userDetailsEntity) {
        return Mono.just("Hello " + userDetailsEntity.getEmail());
    }
}
