package com.adamboyd.reactive.models.restmodels.rocks;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Country {
    private String countryName;
    private String isoCountryCode;
}
