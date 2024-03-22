package com.adamboyd.reactive.quarryService.models.businessobjects;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@Table("location")
public class LocationEntity {
    @Id
    private Integer id;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String address;
    private String googleAPIPlaceId;
}
