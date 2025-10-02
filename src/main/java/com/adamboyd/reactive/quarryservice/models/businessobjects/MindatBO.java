package com.adamboyd.reactive.quarryservice.models.businessobjects;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class MindatBO {
    private Long id;
    private String name;
    private String country;
    private Double latitude;
    private Double longitude;

    private String longid;
    private String guid;
    private String locality_type;
    private String txt;
    private String description_short;
    private String elements;
}


