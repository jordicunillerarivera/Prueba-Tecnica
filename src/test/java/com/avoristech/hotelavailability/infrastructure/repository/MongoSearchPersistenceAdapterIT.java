package com.avoristech.hotelavailability.infrastructure.repository;

import com.avoristech.hotelavailability.domain.model.HotelId;
import com.avoristech.hotelavailability.domain.model.Search;
import com.avoristech.hotelavailability.domain.model.SearchPeriod;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@Import(MongoSearchPersistenceAdapter.class)
@Testcontainers
class MongoSearchPersistenceAdapterIT {
    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void setMongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.host", mongo::getHost);
        registry.add("spring.data.mongodb.port", mongo::getFirstMappedPort);
        registry.add("spring.data.mongodb.database", () -> "test");  // opcional: db name
    }

    @Autowired
    private SearchDocumentRepository repository;

    @Autowired
    private MongoSearchPersistenceAdapter adapter;

    @Test
    void saveAndFindById_AndFindSimilar_WorkCorrectly() {
        // Arranque
        Search search = Search.of(
                new HotelId("h-it"),
                new SearchPeriod("15/08/2025", "18/08/2025"),
                List.of(5,10)
        );

        // Act: guardar
        adapter.save(search);

        // Assert: findById
        Search fetched = adapter.findById(search.getSearchId());
        assertThat(fetched).isEqualTo(search);

        // Act: findSimilar
        var similar = adapter.findSimilar(search);

        // Assert: debe incluir el mismo search
        assertThat(similar).hasSize(1)
                .first()
                .satisfies(s -> assertThat(s).isEqualTo(search));
    }
}
