package com.i2iacademy.gridwatch.core.analytics.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class HomeMetrics implements Serializable {

    private Long homeId;
    private BigDecimal accumulatedCost = BigDecimal.ZERO;
    private BigDecimal accumulatedWatt = BigDecimal.ZERO;
    private boolean penaltyActive = false;
    private Map<Long, Integer> applianceBreachCounters = new HashMap<>();
    private Map<Long, Boolean> applianceAnomalyFlags = new HashMap<>();
}