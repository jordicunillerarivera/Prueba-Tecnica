package com.avoristech.hotelavailability;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = HotelAvailabilityApplication.class)
class HotelAvaillabilityApplicationTest {
    @Test
    void applicationStarts() {
        HotelAvailabilityApplication.main(new String[]{});
        assertTrue(true);
    }
}
