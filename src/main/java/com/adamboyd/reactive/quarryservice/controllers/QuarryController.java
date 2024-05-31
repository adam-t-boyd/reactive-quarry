package com.adamboyd.reactive.quarryservice.controllers;

import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryservice.services.QuarryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/quarry")
@RequiredArgsConstructor
@Tag(name = "Quarry",
        description = "Create, manage and retrieve info of quarries using Google's official 'places' APIs.")
public class QuarryController {
    @NonNull
    private final QuarryService quarryService;

    @Operation(summary = "Retrieves all quarries.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Flux.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No record found.",
                    content = @Content)})
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<Quarry> getQuarries() {
        return quarryService.getQuarries();
    }

    @Operation(summary = "Retrieves quarry info by id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Quarry.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No record found.",
                    content = @Content)})
    @GetMapping("/{quarryId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Quarry> getQuarry(
            @Parameter(description = "quarry id to be retrieved.")
            @PathVariable BigDecimal quarryId) {
        return quarryService.getQuarry(quarryId);
    }

    @Operation(summary = "Create a new quarry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Quarry.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No record found.",
                    content = @Content)})
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Quarry> createQuarry(@RequestBody Quarry quarry) {
        return quarryService.createQuarry(quarry);
    }

    @Operation(summary = "Update a quarry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Quarry.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No record found.",
                    content = @Content)})
    @PutMapping("/quarryId")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Quarry> updateQuarry(
            @Parameter(description = "quarry id to be updated.")
            @PathVariable() BigDecimal quarryId,
            @RequestBody Quarry quarry) {
        return quarryService.updateQuarry(quarryId, quarry);
    }

    @Operation(summary = "Delete a quarry.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Accepted.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Quarry.class))}),
            @ApiResponse(responseCode = "400", description = "Bad request.",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No record found.",
                    content = @Content)})
    @DeleteMapping("/quarryId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteQuarry(
            @Parameter(description = "quarry id to be deleted.")
            @PathVariable("quarryId") BigDecimal quarryId) {
        return quarryService.deleteQuarry(quarryId);
    }
}
