package com.i2iacademy.gridwatch.sensors.messaging;

import com.i2iacademy.gridwatch.sensors.config.KafkaTopics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TelemetryProducerService {

    private final KafkaTemplate<String, TelemetryEvent> kafkaTemplate;

    public void publish(TelemetryEvent event) {
        log.debug("Publishing telemetry: homeId={}, applianceId={}, watt={}",
                event.getHomeId(), event.getApplianceId(), event.getCurrentWatt());
        kafkaTemplate.send(KafkaTopics.TELEMETRY_TOPIC, event.getHomeId().toString(), event);
    }
}