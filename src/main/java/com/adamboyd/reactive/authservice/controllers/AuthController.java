package com.adamboyd.reactive.authservice.controllers;

import com.adamboyd.reactive.authservice.restmodels.*;
import com.adamboyd.reactive.authservice.services.UserService;
import com.adamboyd.reactive.authservice.utils.AuthManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Register a new user, and login/verify as an existing user.")
@Slf4j
public class AuthController {
    @NonNull
    private final UserService userService;
    @NonNull
    private final AuthManager authManager;

    @Operation(summary = "Login and receive an authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Login successful.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No user found with email example@gmail.com," +
                    " please register via POST '/register' API.",
                    content = @Content)})
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthenticationResponse> login(@Valid @RequestBody(description = "User credentials.", required = true,
            content = @Content(schema = @Schema(implementation = AuthenticationRequest.class)))
                                              @org.springframework.web.bind.annotation.RequestBody
                                              AuthenticationRequest authenticationRequest) {
        return authManager.authenticate(authenticationRequest);
    }

    @Operation(summary = "Register and receive an authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Welcome! Here's your JWT token EyJhbGciOiJIUzI1NiIsInR" +
                    "5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IlF1aW5jeSBMYXJzb24iLCJpYXQiOjE1MTYyMzkwMj",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid register details supplied.",
                    content = @Content)})
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public Mono<AuthenticationResponse> register(
            @RequestBody(description = "New user to be registered.", required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid @org.springframework.web.bind.annotation.RequestBody RegisterRequest registerRequest) {
        return userService.createUser(registerRequest);
    }

    @Operation(summary = "Authenticate the JWT token of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "JWT authentication worked!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid username or token.",
                    content = @Content)})
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<ResponseEntity<String>> verifyToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(String.format("Missing or invalid token: %s", authHeader)));
        }

        return authManager.authenticate(new BearerToken(authHeader.substring(7)))
                .map(auth -> ResponseEntity.ok("Token is valid for user: " + auth.getName()))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(error.getMessage())));
    }
}
