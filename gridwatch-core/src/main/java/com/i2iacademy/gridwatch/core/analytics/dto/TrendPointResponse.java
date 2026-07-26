package com.i2iacademy.gridwatch.core.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrendPointResponse {
    private LocalDate date;
    private BigDecimal totalWatt;
    private BigDecimal totalCost;
}