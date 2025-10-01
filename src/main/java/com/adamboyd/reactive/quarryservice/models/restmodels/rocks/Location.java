package com.adamboyd.reactive.quarryservice.models.restmodels.rocks;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class Location {
     BigDecimal latitude;
     BigDecimal longitude;
     String address;
     Country country;
     String googleAPIPlaceId;
}
