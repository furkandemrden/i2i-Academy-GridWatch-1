package com.i2iacademy.gridwatch.sensors.messaging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HomeRegistrationEvent {
    private Long homeId;
    private String homeName;
}