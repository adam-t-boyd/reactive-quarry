package com.adamboyd.reactive.quarryservice.models.businessobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MindatGeoLocResponse {
    private List<MindatLocalitySummary> results;
    // getters/setters
}
