package com.adamboyd.reactive.restmodels.rocks;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Location {
    final BigDecimal latitude;
    final BigDecimal longitude;
    final String address;
    final Country country;
    final String googleAPIPlaceId;
}
