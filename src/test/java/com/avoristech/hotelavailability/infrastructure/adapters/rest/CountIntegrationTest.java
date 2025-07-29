// src/test/java/com/avoristech/hotelavailability/adapters/rest/CountIntegrationTest.java
package com.avoristech.hotelavailability.infrastructure.adapters.rest;

import com.avoristech.hotelavailability.infrastructure.repository.SearchDocument;
import com.avoristech.hotelavailability.infrastructure.repository.SearchDocumentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class CountIntegrationTest {
    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

    @DynamicPropertySource
    static void mongoProps(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SearchDocumentRepository repository;

    private String id;

    @BeforeEach
    void setup() {
        // Inserto tres documentos: dos iguales y uno distinto
        LocalDate in  = LocalDate.of(2025, 2, 1);
        LocalDate out = LocalDate.of(2025, 2, 3);
        id = "testcount1";
        SearchDocument doc1 = new SearchDocument(id, "h1", in, out, List.of(10,20));
        SearchDocument doc2 = new SearchDocument("other1", "h1", in, out, List.of(10,20));
        SearchDocument doc3 = new SearchDocument("other2", "h1", in, out, List.of(20,10));
        repository.saveAll(List.of(doc1, doc2, doc3));
    }

    @Test
    void getCount_ReturnsCorrectJson() throws Exception {
        mockMvc.perform(get("/count")
                        .param("searchId", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").value(id))
                .andExpect(jsonPath("$.count").value(3))
                .andExpect(jsonPath("$.search.hotelId").value("h1"))
                .andExpect(jsonPath("$.search.checkIn").value("01/02/2025"))
                .andExpect(jsonPath("$.search.checkOut").value("03/02/2025"))
                .andExpect(jsonPath("$.search.ages").isArray());
    }
}
