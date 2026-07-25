package com.i2iacademy.gridwatch.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class GridwatchCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(GridwatchCoreApplication.class, args);
	}

}