package com.adamboyd.reactive.userService;

import com.adamboyd.reactive.authService.restmodels.RegisterRequest;
import com.adamboyd.reactive.authService.services.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userDetailsService;

    @PutMapping("/{userId}")
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable BigDecimal userId,
            @RequestBody RegisterRequest updateRequest) {
        return userDetailsService
                .updateUser(userId, updateRequest)
                .flatMap(optionalUser -> Mono.just(ResponseEntity.status(ACCEPTED).body(optionalUser)));
    }

    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUser(
            @PathVariable BigDecimal userId,
            @RequestBody RegisterRequest updateRequest) {
        return userDetailsService
                .deleteUser(userId, updateRequest)
                .flatMap(optionalUser -> Mono.just(ResponseEntity.status(NO_CONTENT).body(optionalUser)));
    }

}
