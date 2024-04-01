package com.adamboyd.reactive.quarryService.models.businessobjects;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("quarry")
public class QuarryBO {
    @Id
    private Integer id;
    private Integer placeid;
    private Boolean disused;
    private LocalDateTime establisheddate;
}
