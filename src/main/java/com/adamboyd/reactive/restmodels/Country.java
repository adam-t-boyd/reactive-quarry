package com.adamboyd.reactive.restmodels;

import lombok.Builder;
import lombok.Data;

import java.util.Locale;

@Builder
@Data
public class Country {
    final String countryName;
    final Locale.IsoCountryCode isoCountryCode;
}
