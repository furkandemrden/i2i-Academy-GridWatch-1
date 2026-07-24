package com.i2iacademy.gridwatch.sensors.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceInfo {
    private Long applianceId;
    private String name;
    private BigDecimal safeLimitWatt;
}