package com.avoristech.hotelavailability.infrastructure.kafka;

import com.avoristech.hotelavailability.infrastructure.adapters.messaging.SearchMessageDTO;
import com.avoristech.hotelavailability.domain.port.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class KafkaSearchConsumerTest {
    @Test
    void consume_CallsPersistencePort_WithMappedSearch() {
        // Arranque
        SearchPersistencePort persistencePort = mock(SearchPersistencePort.class);
        KafkaSearchConsumer consumer = new KafkaSearchConsumer(persistencePort);

        String id = "testSearchId";
        String hotelId = "testHotel";
        String checkIn = "01/01/2025";
        String checkOut = "03/01/2025";
        List<Integer> ages = List.of(1,2,3);

        SearchMessageDTO dto = new SearchMessageDTO(id, hotelId, checkIn, checkOut, ages);

        // Act
        consumer.consume(dto);

        // Assert
        ArgumentCaptor<Search> captor = ArgumentCaptor.forClass(Search.class);
        verify(persistencePort, times(1)).save(captor.capture());

        Search saved = captor.getValue();
        assertEquals(id, saved.getSearchId());
        assertEquals(hotelId, saved.getHotelId().value());
        assertEquals(new SearchPeriod(checkIn, checkOut), saved.getPeriod());
        assertEquals(ages, saved.getAges());
    }
}
