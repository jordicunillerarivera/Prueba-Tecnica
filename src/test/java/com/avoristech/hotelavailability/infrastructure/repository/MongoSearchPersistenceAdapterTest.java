package com.avoristech.hotelavailability.infrastructure.repository;

import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MongoSearchPersistenceAdapterTest {
    private SearchDocumentRepository repository;
    private MongoSearchPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        repository = mock(SearchDocumentRepository.class);
        adapter = new MongoSearchPersistenceAdapter(repository);
    }

    @Test
    void save_CreateAndSavesDocument() {
        // Arranque
        Search search = Search.of(
                new HotelId("h1"),
                new SearchPeriod("01/01/2026", "02/01/2026"),
                List.of(20,30)
        );

        // Act
        adapter.save(search);

        // Assert
        ArgumentCaptor<SearchDocument> captor = ArgumentCaptor.forClass(SearchDocument.class);
        verify(repository).save(captor.capture());
        SearchDocument doc = captor.getValue();

        assertEquals(search.getSearchId(), doc.getSearchId());
        assertEquals(search.getHotelId().value(), doc.getHotelId());
        assertEquals(search.getPeriod().getCheckIn(), doc.getCheckIn());
        assertEquals(search.getPeriod().getCheckOut(), doc.getCheckOut());
        assertEquals(search.getAges(), doc.getAges());
    }

    @Test
    void findById_WhenFound_ReturnsDomainObject() {
        // Arranque
        Search search = Search.of(
                new HotelId("h2"),
                new SearchPeriod("05/05/2026", "06/05/2026"),
                List.of(25)
        );

        SearchDocument doc = new SearchDocument(
                search.getSearchId(),
                search.getHotelId().value(),
                search.getPeriod().getCheckIn(),
                search.getPeriod().getCheckOut(),
                search.getAges()
        );

        when(repository.findById(search.getSearchId()))
                .thenReturn(Optional.of(doc));

        //Act
        Search result = adapter.findById(search.getSearchId());

        // Assert
        assertEquals(search, result);
    }

    @Test
    void findById_WhenNotFound_Throws() {
        // Arranque
        when(repository.findById("nada")).thenReturn(Optional.empty());

        //Act y Assert
        assertThrows(IllegalArgumentException.class, () ->
                adapter.findById("nada")
        );
    }

    @Test
    void findSimilar_FiltersByHotelIdAndPeriod_AndMatchesAgesUnordered() {
        // Arranque
        Search original = Search.of(
                new HotelId("h3"),
                new SearchPeriod("10/10/2026", "12/10/2026"),
                List.of(1,2,3)
        );

        // Documento con edades en distinto orden
        SearchDocument doc1 = new SearchDocument(
                original.getSearchId(),
                "h3",
                original.getPeriod().getCheckIn(),
                original.getPeriod().getCheckOut(),
                List.of(3,1,2)
        );

        // Documento con diferente conjunto de edades
        SearchDocument doc2 = new SearchDocument(
                "otro",
                "h3,",
                original.getPeriod().getCheckIn(),
                original.getPeriod().getCheckOut(),
                List.of(1,2,4)
        );

        when(repository.findByHotelIdAndCheckInAndCheckOut(
                "h3",
                original.getPeriod().getCheckIn(),
                original.getPeriod().getCheckOut()
        )).thenReturn(List.of(doc1, doc2));

        // Act
        var result = adapter.findSimilar(original);

        // Assert
        assertEquals(1,result.size());
        assertEquals(original.getSearchId(), result.get(0).getSearchId());
    }
}
