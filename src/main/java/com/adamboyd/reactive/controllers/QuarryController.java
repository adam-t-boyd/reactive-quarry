package com.adamboyd.reactive.controllers;

import com.adamboyd.reactive.restmodels.rocks.Quarry;
import com.adamboyd.reactive.services.QuarryService;
import lombok.RequiredArgsConstructor;
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
    public Flux<Quarry> getQuarries() {
        return quarryService.getQuarries();
    }

    @GetMapping("/{quarryId}")
    public Mono<Quarry> getQuarry(@PathVariable BigDecimal quarryId) {
        return quarryService.getQuarry(quarryId);
    }

    @PostMapping()
    public Mono<Quarry> createQuarry(@RequestBody Quarry quarry) {
        return quarryService.createQuarry(quarry);
    }

    @PutMapping("/quarryId")
    public Mono<Quarry> updateQuarry(@PathVariable("quarryId") BigDecimal quarryId,
                                     @RequestBody Quarry quarry) {
        return quarryService.updateQuarry(quarryId, quarry);
    }

    @DeleteMapping("/quarryId")
    public Mono<Void> deleteQuarry(@PathVariable("quarryId") BigDecimal quarryId) {
        return quarryService.deleteQuarry(quarryId);
    }
}
