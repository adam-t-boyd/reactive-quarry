package com.adamboyd.reactive.quarryService.models.businessobjects;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Builder
@Table("country")
public class CountryEntity {
    @Id
    private Integer id;
    private String countryName;
    private String isoCountryCode;
}
