package com.avoristech.hotelavailability.application.service;

import com.avoristech.hotelavailability.domain.port.SearchPersistencePort;
import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchCount;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CountServiceTest {
    @Test
    void countSearch_ReturnsCorrectCountAndSearch() {
        SearchPersistencePort persistencePort = Mockito.mock(SearchPersistencePort.class);
        CountService countService = new CountService(persistencePort);

        Search original = Search.of(
                new HotelId("h1"),
                new SearchPeriod("01/01/2025", "02/01/2025"),
                List.of(10,20)
        );
        List<Search> similars = List.of(original, original);

        Mockito.when(persistencePort.findById(original.getSearchId())).thenReturn(original);
        Mockito.when(persistencePort.findSimilar(original)).thenReturn(similars);

        SearchCount searchCount = countService.countSearch(original.getSearchId());

        assertEquals(original.getSearchId(), searchCount.searchId());
        assertEquals(original, searchCount.search());
        assertEquals(2, searchCount.count());
    }

    @Test
    void countSearch_WhenNotFound_PropagateException() {
        SearchPersistencePort persistencePort = Mockito.mock(SearchPersistencePort.class);
        CountService service = new CountService(persistencePort);

        Mockito.when(persistencePort.findById("nada"))
                .thenThrow(new IllegalArgumentException("not found"));

        assertThrows(IllegalArgumentException.class, () ->
                service.countSearch("nada"));
    }
}
