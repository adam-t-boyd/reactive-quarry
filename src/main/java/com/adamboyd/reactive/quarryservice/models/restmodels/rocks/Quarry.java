package com.adamboyd.reactive.quarryservice.models.restmodels.rocks;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quarry {
    @Schema(description = "Unique identifier for the quarry")
    @NotNull
    BigDecimal quarryId;

    @Schema(description = "Location of the quarry")
    @NotBlank
    Location location;

    @Schema(description = "Date when the quarry was established")
    @NotBlank
    ZonedDateTime establishedDate;

    @Schema(description = "Flag indicating whether the quarry is disused")
    @NotNull
    boolean disused;
}
