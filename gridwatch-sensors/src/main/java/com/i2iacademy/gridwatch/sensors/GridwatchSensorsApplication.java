package com.i2iacademy.gridwatch.sensors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GridwatchSensorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(GridwatchSensorsApplication.class, args);
    }

}