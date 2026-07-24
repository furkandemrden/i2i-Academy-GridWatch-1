package com.i2iacademy.gridwatch.core.home.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HomeRegistrationRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String contactEmail;

    @Positive
    private BigDecimal budgetQuota;

    @Positive
    private BigDecimal normalRate;

    @Positive
    private BigDecimal penaltyRate;

    @NotEmpty
    private List<@Valid ApplianceRequest> appliances;
}