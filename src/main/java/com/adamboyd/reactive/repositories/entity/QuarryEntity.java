package com.adamboyd.reactive.repositories.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@Table("quarry")
public class QuarryEntity {
    @Id
    private Integer id;
    private Boolean disused;
    private LocalDateTime establisheddate;
}
