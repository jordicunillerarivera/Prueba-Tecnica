package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.application.port.out.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.*;

class KafkaSearchConsumerTest {
    @Test
    void consume_CallsPersistencePort() {
        // Arranque
        SearchPersistencePort persistencePort = mock(SearchPersistencePort.class);
        KafkaSearchConsumer consumer = new KafkaSearchConsumer(persistencePort);

        Search search = Search.of(
                new HotelId("testHotell"),
                new SearchPeriod("01/01/2025", "03/01/2025"),
                List.of(1,2,3)
        );

        // Act
        consumer.consume(search);

        // Assert
        verify(persistencePort, times(1)).save(search);
    }
}
