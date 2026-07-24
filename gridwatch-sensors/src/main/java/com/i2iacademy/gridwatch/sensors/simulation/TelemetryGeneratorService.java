package com.i2iacademy.gridwatch.sensors.simulation;

import com.i2iacademy.gridwatch.sensors.messaging.TelemetryEvent;
import com.i2iacademy.gridwatch.sensors.messaging.TelemetryProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.OffsetDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelemetryGeneratorService {

    private final SimulationRegistry simulationRegistry;
    private final TelemetryProducerService producerService;

    @Scheduled(fixedRate = 5000)
    public void generateTelemetry() {
        var appliances = simulationRegistry.getAllAppliances();

        if (appliances.isEmpty()) {
            return;
        }

        for (SimulatedAppliance appliance : appliances) {
            BigDecimal currentWatt = generateRealisticWatt(appliance.getSafeLimitWatt());

            TelemetryEvent event = new TelemetryEvent(
                    appliance.getHomeId(),
                    appliance.getApplianceId(),
                    currentWatt,
                    OffsetDateTime.now()
            );

            producerService.publish(event);
        }

        log.debug("Telemetry cycle complete: {} appliances processed", appliances.size());
    }

    private BigDecimal generateRealisticWatt(BigDecimal safeLimitWatt) {
        // %70 ihtimalle güvenli aralıkta (limitin %40-%90'ı), %30 ihtimalle limiti aşan bir değer (limitin %95-%130'u)
        double random = ThreadLocalRandom.current().nextDouble();
        double factor;

        if (random < 0.7) {
            factor = 0.4 + ThreadLocalRandom.current().nextDouble() * 0.5; // 0.40 - 0.90
        } else {
            factor = 0.95 + ThreadLocalRandom.current().nextDouble() * 0.35; // 0.95 - 1.30
        }

        return safeLimitWatt.multiply(BigDecimal.valueOf(factor))
                .setScale(2, RoundingMode.HALF_UP);
    }
}