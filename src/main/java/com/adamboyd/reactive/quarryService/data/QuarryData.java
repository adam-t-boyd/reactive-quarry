package com.adamboyd.reactive.quarryService.data;

import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Country;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Location;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Arrays;

import static java.util.Locale.getISOCountries;

public class QuarryData {

    public static Quarry buildQuarry() {
        return Quarry.builder()
                .quarryId(BigDecimal.valueOf(1))
                .establishedDate(ZonedDateTime.now())
                .disused(true)
                .location(Location.builder()
                        .address("Address")
                        .country(Country.builder()
                                .countryName("Northern Ireland")
                                .isoCountryCode(Arrays.stream(getISOCountries())
                                        .filter(x -> x.contains("GB")).toList().get(0))
                                .build())
                        .latitude(new BigDecimal("43.40"))
                        .longitude(new BigDecimal("44.56"))
                        .build())
                .build();
    }
}
