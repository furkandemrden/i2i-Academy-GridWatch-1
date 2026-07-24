package com.i2iacademy.gridwatch.core.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TelemetryEvent {
    private Long homeId;
    private Long applianceId;
    private BigDecimal currentWatt;
    private OffsetDateTime timestamp;
}