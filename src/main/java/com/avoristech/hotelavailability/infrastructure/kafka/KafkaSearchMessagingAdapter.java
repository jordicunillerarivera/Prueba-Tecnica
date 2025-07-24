package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.application.port.out.SearchMessagingPort;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.infrastructure.config.constants.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchMessagingAdapter implements SearchMessagingPort {
    private static final String TOPIC = KafkaTopics.HOTEL_AVAILABILITY_SEARCHES;

    private final KafkaTemplate<String, Search> kafkaTemplate;

    public KafkaSearchMessagingAdapter(KafkaTemplate<String, Search> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Search search) {
        kafkaTemplate.send(TOPIC, search.getSearchId(), search);
    }
}
