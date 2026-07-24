package com.i2iacademy.gridwatch.sensors.messaging;

import com.i2iacademy.gridwatch.sensors.config.KafkaTopics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HomeRegistrationConsumerService {

    @KafkaListener(topics = KafkaTopics.ASSET_REGISTRATION_TOPIC, groupId = "gridwatch-sensors")
    public void consume(HomeRegistrationEvent event) {
        log.info("New home registered for simulation: homeId={}, homeName={}",
                event.getHomeId(), event.getHomeName());
        // TODO: Bu evi simülasyon listesine ekle (bir sonraki adım)
    }
}