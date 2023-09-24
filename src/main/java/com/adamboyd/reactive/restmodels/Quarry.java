package com.adamboyd.reactive.restmodels;

import lombok.Builder;
import lombok.Data;

import java.time.ZonedDateTime;

@Builder
@Data
public class Quarry {
    final Location location;
    final ZonedDateTime establishedDate;
    final Boolean disused;
}
