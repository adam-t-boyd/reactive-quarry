package com.adamboyd.reactive.authservice.controllers;

import com.adamboyd.reactive.authservice.restmodels.*;
import com.adamboyd.reactive.authservice.services.JwtService;
import com.adamboyd.reactive.authservice.services.UserServiceImpl;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Register a new user, and login/verify as an existing user.")
public class AuthController {
    @NonNull private final UserServiceImpl userDetailsService;
    @NonNull private final JwtService jwtService;
    @NonNull private final PasswordEncoder passwordEncoder;
    private static final String WELCOME_TOKEN_MSG = "Welcome! Here's your JWT token %s";

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
    public Mono<String> login(@RequestBody AuthenticationRequest authenticationRequest) {
        Mono<UserDTO> userByEmail = userDetailsService.getUserByEmail(authenticationRequest.getEmail());
        return userByEmail
                .map(Optional::ofNullable)
                .flatMap(optionalUser -> {
                    if (optionalUser.isEmpty()) {
                        throw new NotFoundException(String.format("No user found with email %s, please register" +
                                                " via POST '/register' API.",
                                authenticationRequest.getEmail()));
                    } else if (passwordEncoder.matches(
                            authenticationRequest.getPassword(), optionalUser.get().getPassword())) {
                        return Mono.just(jwtService.generateToken(optionalUser.get().getUsername()));
                    }
                    throw new UsernameNotFoundException("Invalid credentials.");
                })
                .onErrorResume(Mono::error);
    }

    // login works, but doesn't decrypt the db password. Needs to.
    // register needs to encode password to store in backend. needs to be available without token.
    // - need to do exception handling. throw error that are handled by controller advice.
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
    public Mono<String> register(
            @RequestBody(description = "New user to be registered.", required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid RegisterRequest registerRequest) {
        final Mono<AuthenticationResponse> userDetails = userDetailsService.createUser(registerRequest);
        return userDetails
                .map(authenticationResponse ->
                    String.format(WELCOME_TOKEN_MSG, authenticationResponse.getToken()));
    }

    @Operation(summary = "Authenticate the JWT token of a user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "JWT authentication worked!",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = String.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid username or token.",
                    content = @Content)})
    @GetMapping("/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> authenticateUser(
            @RequestBody(description = "Authentication details of user.", required = true,
            content = @Content(schema = @Schema(implementation = AuthenticationResponse.class)))
                                             @Valid AuthenticationResponse authenticationResponse) {
        // WIP
        boolean isTokenValid = jwtService.isTokenValid(authenticationResponse.getToken(),
                UserDetailsBO.builder().build());
        if (isTokenValid) {
            Mono.just("JWT authentication worked!");
        }
        throw new BadRequestException("Invalid username or token.");
    }
}
