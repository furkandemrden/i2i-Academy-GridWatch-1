package com.i2iacademy.gridwatch.sensors.messaging;

import com.i2iacademy.gridwatch.sensors.config.KafkaTopics;
import com.i2iacademy.gridwatch.sensors.simulation.SimulationRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HomeRegistrationConsumerService {

    private final SimulationRegistry simulationRegistry;

    @KafkaListener(topics = KafkaTopics.ASSET_REGISTRATION_TOPIC, groupId = "gridwatch-sensors")
    public void consume(HomeRegistrationEvent event) {
        log.info("New home registered for simulation: homeId={}, homeName={}",
                event.getHomeId(), event.getHomeName());
        simulationRegistry.registerHome(event);
    }
}