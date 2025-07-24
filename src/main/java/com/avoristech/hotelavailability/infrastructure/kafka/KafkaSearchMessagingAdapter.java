package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.application.port.out.SearchMessagingPort;
import com.avoristech.hotelavailability.domain.model.Search;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaSearchMessagingAdapter implements SearchMessagingPort {
    private static final String TOPIC = "hotel_availability_searches"; //TODO revisar literal

    private final KafkaTemplate<String, Search> kafkaTemplate;

    public KafkaSearchMessagingAdapter(KafkaTemplate<String, Search> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Search search) {
        kafkaTemplate.send(TOPIC, search.getSearchId(), search);
    }
}
