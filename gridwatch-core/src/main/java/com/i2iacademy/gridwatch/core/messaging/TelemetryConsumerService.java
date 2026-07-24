package com.i2iacademy.gridwatch.core.messaging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.i2iacademy.gridwatch.core.config.KafkaTopicConfig.TELEMETRY_TOPIC;

@Slf4j
@Service
public class TelemetryConsumerService {

    @KafkaListener(topics = TELEMETRY_TOPIC, groupId = "gridwatch-core")
    public void consume(TelemetryEvent event) {
        log.info("Received telemetry: homeId={}, applianceId={}, watt={}, timestamp={}",
                event.getHomeId(), event.getApplianceId(), event.getCurrentWatt(), event.getTimestamp());
        // TODO: Ignite güncellemesi burada yapılacak (bir sonraki adım)
    }
}