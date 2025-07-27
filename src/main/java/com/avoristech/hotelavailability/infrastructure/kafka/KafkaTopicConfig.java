package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.infrastructure.config.constants.KafkaTopics;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    @Bean
    public NewTopic hotelAvailabilitySearchesTopic() {
        return TopicBuilder
                .name(KafkaTopics.HOTEL_AVAILABILITY_SEARCHES)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
