package com.i2iacademy.gridwatch.core.messaging;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.i2iacademy.gridwatch.core.config.KafkaTopicConfig.ASSET_REGISTRATION_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeRegistrationProducerService {

    private final KafkaTemplate<String, HomeRegistrationEvent> kafkaTemplate;

    public void publish(HomeRegistrationEvent event) {
        log.info("Publishing home registration: homeId={}, homeName={}", event.getHomeId(), event.getHomeName());
        kafkaTemplate.send(ASSET_REGISTRATION_TOPIC, event.getHomeId().toString(), event);
    }
}