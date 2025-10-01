package com.adamboyd.reactive.quarryService.services;

import com.adamboyd.reactive.quarryService.mappers.QuarryMapper;
import com.adamboyd.reactive.quarryService.models.restmodels.rocks.Quarry;
import com.adamboyd.reactive.quarryService.repositories.QuarryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class QuarryServiceImpl implements QuarryService {
    private final QuarryRepository quarryRepository;
    private static final QuarryMapper quarryMapper = QuarryMapper.INSTANCE;

    private static final String GOOGLE_PLACES_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json";

    private final WebClient.Builder webClientBuilder;

    @Value("${google.places.api-key}")
    private final String apiKey;

    public Mono<QuarryResponse> findQuarries(String countryCode, Double latitude, Double longitude) {
        String uri = buildUri(countryCode, latitude, longitude);

        return webClientBuilder.get()
                .uri(uri)
                .retrieve()
                .onStatus(
                        status -> status.isError(),
                        response -> response.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(
                                        new ExternalApiException("Google Places API error: " + body)))
                )
                .bodyToMono(QuarryResponse.class);
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

    @Override
    public Flux<Quarry> getQuarries() {
        return quarryRepository
                .findAll()
                .map(quarryMapper::toQuarry);
    }

    @Override
    public Mono<Quarry> getQuarry(BigDecimal quarryId) {
        return quarryRepository
                .findById(quarryId)
                .map(quarryMapper::toQuarry);
    }

    @Override
    public Mono<Quarry> createQuarry(Quarry quarry) {
        return quarryRepository.save(quarryMapper.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Quarry> updateQuarry(BigDecimal quarryId, Quarry quarry) {
        return quarryRepository.save(quarryMapper.toQuarryBO(quarry))
                .map(quarryBO -> quarry);
    }

    @Override
    public Mono<Void> deleteQuarry(BigDecimal quarryId) {
        return quarryRepository.deleteById(quarryId);
    }
}
