package com.i2iacademy.gridwatch.core.analytics.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeStatusResponse {
    private Long homeId;
    private BigDecimal accumulatedWatt;
    private BigDecimal accumulatedCost;
    private boolean penaltyActive;
    private Map<Long, Boolean> applianceAnomalyFlags;
}