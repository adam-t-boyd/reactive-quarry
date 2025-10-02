package com.adamboyd.reactive.quarryservice.services;

import com.adamboyd.reactive.quarryservice.models.businessobjects.MindatBO;
import com.adamboyd.reactive.quarryservice.models.businessobjects.MindatGeoLocResponse;
import com.adamboyd.reactive.quarryservice.models.businessobjects.MindatLocalityListResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class MindatServiceImpl implements MindatService {

        @NonNull
        final WebClient.Builder webClientBuilder;

        @Value("${mindat.api.url:https://api.mindat.org/v1}")
        @NonNull
        private final String baseUrl;

        @Value("${mindat.api.token}")
        @NonNull
        private final String apiToken;

        @Override
        public Flux<MindatBO> findLocalities(Double latitude, Double longitude, int range) {
                // POST to /geoloc-point/
                return webClientBuilder.baseUrl(baseUrl).build().post()
                        .uri("/geoloc-point/")
                        .header("Authorization", "Bearer " + apiToken)
                        .bodyValue(Map.of(
                                "latitude", latitude,
                                "longitude", longitude,
                                "range", range
                        ))
                        .retrieve()
                        .bodyToMono(MindatGeoLocResponse.class) // response containing locality IDs
                        .flatMapMany(resp -> Flux.fromIterable(resp.getResults()))
                        .flatMap(locality -> getLocalityDetails(locality.getId())); // expand each locality
        }

        @Override
        public Flux<MindatBO> findLocalities(String countryCode) {
                // GET /localities/?country={countryCode}
                return webClientBuilder.baseUrl(baseUrl).build().get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/localities/")
                                .queryParam("country", countryCode)
                                .build())
                        .header("Authorization", "Bearer " + apiToken)
                        .retrieve()
                        .bodyToMono(MindatLocalityListResponse.class)
                        .flatMapMany(resp -> Flux.fromIterable(resp.getResults()))
                        .flatMap(locality -> getLocalityDetails(locality.getId()));
        }

        private Mono<MindatBO> getLocalityDetails(Long localityId) {
                return webClientBuilder.baseUrl(baseUrl).build().get()
                        .uri("/localities/{id}/", localityId)
                        .header("Authorization", "Bearer " + apiToken)
                        .retrieve()
                        .bodyToMono(MindatBO.class);
        }
}
