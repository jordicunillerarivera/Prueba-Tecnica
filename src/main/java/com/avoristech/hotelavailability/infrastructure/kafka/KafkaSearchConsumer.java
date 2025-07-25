package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.application.port.out.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.infrastructure.config.constants.KafkaTopics;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchConsumer {
    private final SearchPersistencePort persistencePort;

    public KafkaSearchConsumer(SearchPersistencePort persistencePort) {
        this.persistencePort = persistencePort;
    }

    @KafkaListener(topics = KafkaTopics.HOTEL_AVAILABILITY_SEARCHES)
    public void consume(Search search) {
        persistencePort.save(search);
    }
}
