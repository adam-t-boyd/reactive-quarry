package com.adamboyd.reactive.businessobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    private BigDecimal id;
    private BigDecimal brand;
    private BigDecimal kilowatt;
}
