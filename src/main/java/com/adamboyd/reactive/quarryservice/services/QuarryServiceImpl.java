package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.common.mappers.exception.ExternalApiException;
import com.adamboyd.reactive.quarryservice.mappers.QuarryMapper;
import com.adamboyd.reactive.quarryservice.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryservice.repositories.QuarryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class QuarryServiceImpl implements QuarryService {
    private final QuarryRepository quarryRepository;
    private static final QuarryMapper QUARRY_MAPPER = QuarryMapper.INSTANCE;
    private static final String GOOGLE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    private final WebClient.Builder webClientBuilder;

    @Value("${google.places.api-key}")
    private final String apiKey;

    public Flux<Quarry> getQuarries(final String countryCode, final Double latitude, final Double longitude) {
        // Client → Your API → (Mindat API) → list of localities/quarries
        // → (for each) → (Google Places API) → merge → return enriched result

        return webClientBuilder.build().get()
                .uri(buildUri(countryCode, latitude, longitude))
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new ExternalApiException("Google Places API error: " + body)))
                )
                .bodyToFlux(Quarry.class);
    }

    @Override
    public Mono<Quarry> getQuarry(BigDecimal quarryId) {
        return quarryRepository
                .findById(quarryId)
                .map(QUARRY_MAPPER::toQuarry);
    }

    @Override
    public Mono<Quarry> createQuarry(Quarry quarry) {
        return quarryRepository.save(QUARRY_MAPPER.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry) {
        return quarryRepository.save(QUARRY_MAPPER.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Void> deleteQuarry(BigDecimal quarryId) {
        return quarryRepository.deleteById(quarryId);
    }

    private String buildUri(String countryCode, Double latitude, Double longitude) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder
                .fromHttpUrl(GOOGLE_PLACES_URL)
                .queryParam("key", apiKey)
                .queryParam("query", "quarry");

        if (countryCode != null) {
            uriBuilder.queryParam("region", countryCode);
        } else if (latitude != null && longitude != null) {
            uriBuilder.queryParam("location", latitude + "," + longitude)
                    .queryParam("radius", 50000); // 50 km
        }
        return uriBuilder.toUriString();
    }
}
