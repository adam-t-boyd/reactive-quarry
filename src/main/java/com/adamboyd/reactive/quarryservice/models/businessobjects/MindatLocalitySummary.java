package com.adamboyd.reactive.quarryservice.models.businessobjects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MindatLocalitySummary {
    private Long id;
    private String name;
}
