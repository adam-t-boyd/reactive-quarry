package com.adamboyd.reactive.authservice.controllers;

import com.adamboyd.reactive.authservice.restmodels.RegisterRequest;
import com.adamboyd.reactive.authservice.restmodels.UserDTO;
import com.adamboyd.reactive.authservice.services.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.ACCEPTED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Manage (update and delete) existing users.")
public class UserController {
    @NonNull
    final UserServiceImpl userDetailsService;

    @Operation(summary = "Update a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User deleted",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @PutMapping("/{userId}")
    public Mono<ResponseEntity<UserDTO>> updateUser(
            @Parameter(description = "id of user to be updated")
            @PathVariable BigDecimal userId,
            @RequestBody(description = "Updated user info.", required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid RegisterRequest updateRequest) {
        return userDetailsService
                .updateUser(userId, updateRequest)
                .flatMap(optionalUser -> Mono.just(ResponseEntity.status(ACCEPTED).body(optionalUser)));
    }

    @Operation(summary = "Delete a user by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "User deleted",
                    content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Invalid user id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)})
    @DeleteMapping("/{userId}")
    public Mono<ResponseEntity<Void>> deleteUser(
            @Parameter(description = "id of user to be deleted")
            @PathVariable BigDecimal userId,
            @RequestBody(description = "User to delete.", required = true,
                    content = @Content(schema = @Schema(implementation = RegisterRequest.class)))
            @Valid RegisterRequest updateRequest) {
        return userDetailsService
                .deleteUser(userId, updateRequest)
                .flatMap(optionalUser -> Mono.just(ResponseEntity.status(NO_CONTENT).body(optionalUser)));
    }

}
