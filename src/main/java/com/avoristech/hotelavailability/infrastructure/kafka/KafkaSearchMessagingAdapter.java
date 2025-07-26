package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.adapters.messaging.SearchMessageDTO;
import com.avoristech.hotelavailability.application.port.out.SearchMessagingPort;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import com.avoristech.hotelavailability.infrastructure.config.constants.KafkaTopics;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaSearchMessagingAdapter implements SearchMessagingPort {
    private static final String TOPIC = KafkaTopics.HOTEL_AVAILABILITY_SEARCHES;
    private final KafkaTemplate<String, SearchMessageDTO> kafkaTemplate;

    public KafkaSearchMessagingAdapter(KafkaTemplate<String, SearchMessageDTO> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Search search) {
        var dto = new SearchMessageDTO(
                search.getSearchId(),
                search.getHotelId().value(),
                search.getPeriod().getCheckIn().format(SearchPeriod.FORMATTER),
                search.getPeriod().getCheckOut().format(SearchPeriod.FORMATTER),
                search.getAges()
        );
        kafkaTemplate.send(TOPIC, search.getSearchId(), dto);
    }
}
