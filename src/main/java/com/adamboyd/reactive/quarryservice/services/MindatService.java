package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.quarryservice.models.businessobjects.MindatBO;
import reactor.core.publisher.Flux;

public interface MindatService {
    Flux<MindatBO> findLocalities(final Double latitude, final Double longitude, int range);

    Flux<MindatBO> findLocalities(final String countryCode);
}
