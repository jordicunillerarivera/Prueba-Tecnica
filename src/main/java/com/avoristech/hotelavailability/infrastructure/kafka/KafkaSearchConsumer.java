package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.adapters.messaging.SearchMessageDTO;
import com.avoristech.hotelavailability.application.port.out.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
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
    public void consume(SearchMessageDTO dto) {
        //mapeo DTO a dominio
        var search = new Search(
                dto.searchId(),
                new HotelId(dto.hotelId()),
                new SearchPeriod(dto.checkIn(), dto.checkOut()),
                dto.ages()
        );

        persistencePort.save(search);
    }
}
