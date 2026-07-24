package com.i2iacademy.gridwatch.core.home.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApplianceRequest {

    @NotBlank
    private String name;

    @Positive
    private BigDecimal safeLimitWatt;
}