package com.avoristech.hotelavailability.infrastructure.adapters.rest;

import com.avoristech.hotelavailability.infrastructure.config.constants.ApiEndpoints;
import com.avoristech.hotelavailability.infrastructure.config.constants.KafkaTopics;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@EmbeddedKafka(partitions = 1, topics = {KafkaTopics.HOTEL_AVAILABILITY_SEARCHES})
class SearchIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    private org.apache.kafka.clients.consumer.Consumer<String, Object> consumer;

    @BeforeEach
    void setup() {
        Map<String, Object> props = KafkaTestUtils.consumerProps(
                embeddedKafkaBroker.getBrokersAsString(), "testGroup", "true");
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        consumer = new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(),
                new JsonDeserializer<>(Object.class)
        ).createConsumer();
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, KafkaTopics.HOTEL_AVAILABILITY_SEARCHES);
    }

    @AfterEach
    void tearDown() {
        consumer.close();
    }

    @Test
    void postSearch_ShouldProduceKafkaMessage() throws Exception {
        // Arranque
        String payload = """
                {
                  "hotelId": "123abc",
                  "checkIn": "29/12/2026",
                  "checkOut": "31/12/2026",
                  "ages": [30,29,1,3]
                }
                """;

        // Act
        mockMvc.perform(post(ApiEndpoints.SEARCH)
                .contentType("application/json")
                .content(payload))
               .andExpect(status().isOk());

        // Assert
        ConsumerRecord<String, Object> kafkaRecord = KafkaTestUtils.getSingleRecord(
                consumer, KafkaTopics.HOTEL_AVAILABILITY_SEARCHES);
        assertThat(kafkaRecord.key()).isNotBlank();
        assertThat(kafkaRecord.value()).isNotNull();
    }
}
