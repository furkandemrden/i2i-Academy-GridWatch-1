package com.i2iacademy.gridwatch.core.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ASSET_REGISTRATION_TOPIC = "asset-registration";
    public static final String TELEMETRY_TOPIC = "telemetry";

    @Bean
    public NewTopic assetRegistrationTopic() {
        return TopicBuilder.name(ASSET_REGISTRATION_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic telemetryTopic() {
        return TopicBuilder.name(TELEMETRY_TOPIC)
                .partitions(1)
                .replicas(1)
                .build();
    }
}