package com.avoristech.hotelavailability.application.service;

import com.avoristech.hotelavailability.application.port.out.SearchMessagingPort;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Test
    void createSearch_SendsToMessaginPort_AndReturrnsId() {
        // Arranque
        SearchMessagingPort messagingPort = mock(SearchMessagingPort.class);
        SearchService service = new SearchService(messagingPort);

        HotelId hotelId = new HotelId("1234abc");
        SearchPeriod period = new SearchPeriod("29/12/2026", "31/12/2026");
        Search search = Search.of(hotelId, period, List.of(30,29,1,3));

        // Acto
        String resultId = service.createSearch(search);

        // Assert
        assertEquals(search.getSearchId(), resultId);

        ArgumentCaptor<Search> captor = ArgumentCaptor.forClass(Search.class);
        verify(messagingPort, times(1)).send(captor.capture());
        Search sent = captor.getValue();
        assertEquals(search, sent, "El Search enviado debe ser exactamente el dominio creado");
    }
}
