package com.adamboyd.reactive.restmodels.rocks;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Builder
public record Quarry(BigDecimal quarryId,
                     Location location,
                     ZonedDateTime establishedDate,
                     Boolean disused) {
}
