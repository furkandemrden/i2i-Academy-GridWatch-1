package com.i2iacademy.gridwatch.sensors.simulation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class SimulatedAppliance {
    private Long applianceId;
    private Long homeId;
    private String name;
    private BigDecimal safeLimitWatt;
}