package com.adamboyd.reactive.restmodels.rocks;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Country {
    final String countryName;
    final String isoCountryCode;
}
