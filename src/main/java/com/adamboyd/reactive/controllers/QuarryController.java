package com.adamboyd.reactive.controllers;

import com.adamboyd.reactive.restmodels.rocks.Country;
import com.adamboyd.reactive.restmodels.rocks.Location;
import com.adamboyd.reactive.restmodels.rocks.Quarry;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static java.util.Locale.getISOCountries;

@RestController
@RequestMapping("/quarry")
public class QuarryController {
    @GetMapping("/{quarryId}")
    public Mono<Quarry> getQuarry(@PathVariable Integer quarryId) {
        return Mono.just(Quarry.builder()
                .quarryId(BigDecimal.valueOf(quarryId))
                .establishedDate(ZonedDateTime.now())
                .disused(true)
                .location(Location.builder()
                        .address("Address")
                        .country(Country.builder()
                                .countryName("Northern Ireland")
                                .isoCountryCode(Arrays.stream(getISOCountries()).filter(x -> x.contains("GB")).toList().get(0))
                                .build())
                        .latitude(new BigDecimal("43.40"))
                        .longitude(new BigDecimal("44.56"))
                        .build())
                .build());
    }

    @GetMapping()
    public Quarry getQuarrys() {
        return Quarry.builder()
                .quarryId(BigDecimal.valueOf(1))
                .establishedDate(ZonedDateTime.now())
                .disused(true)
                .location(Location.builder()
                        .address("Address")
                        .country(Country.builder()
                                .countryName("Northern Ireland")
                                .isoCountryCode(Arrays.stream(getISOCountries()).filter(x -> x.contains("GB")).toList().get(0))
                                .build())
                        .latitude(new BigDecimal("43.40"))
                        .longitude(new BigDecimal("44.56"))
                        .build())
                .build();
    }

    @PostMapping()
    public Mono<Quarry> createQuarry(@RequestBody Quarry quarry) {
        return Mono.just(quarry);
    }

    @PutMapping("/quarryId")
    public Mono<Quarry> updateQuarry(@PathVariable("quarryId") BigDecimal quarryId,
                                     @RequestBody Quarry quarry) {
        return Mono.just(quarry);
    }

    @DeleteMapping("/quarryId")
    public Mono<Void> deleteQuarry(@PathVariable("quarryId") BigDecimal quarryId) {
        return Mono.empty();
    }
}
