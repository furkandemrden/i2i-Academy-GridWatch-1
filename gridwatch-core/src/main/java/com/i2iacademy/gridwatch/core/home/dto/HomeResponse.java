package com.i2iacademy.gridwatch.core.home.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeResponse {
    private Long id;
    private String name;
    private String contactEmail;
}