package com.adamboyd.reactive.quarryService.controllers;

import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryService.services.QuarryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@RestController
@RequestMapping("/quarry")
@RequiredArgsConstructor
public class QuarryController {

    private final QuarryService quarryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Flux<Quarry> getQuarries() {
        return quarryService.getQuarries();
    }

    @GetMapping("/{quarryId}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Quarry> getQuarry(@PathVariable BigDecimal quarryId) {
        return quarryService.getQuarry(quarryId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Quarry> createQuarry(@RequestBody Quarry quarry) {
        return quarryService.createQuarry(quarry);
    }

    @PutMapping("/quarryId")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Quarry> updateQuarry(@PathVariable("quarryId") BigDecimal quarryId,
                                     @RequestBody Quarry quarry) {
        return quarryService.updateQuarry(quarryId, quarry);
    }

    @DeleteMapping("/quarryId")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deleteQuarry(@PathVariable("quarryId") BigDecimal quarryId) {
        return quarryService.deleteQuarry(quarryId);
    }
}
