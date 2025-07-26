package com.avoristech.hotelavailability.domain.model;

import static org.junit.jupiter.api.Assertions.*;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;
import org.junit.jupiter.api.Test;

class HotelIdTest {

    @Test
    void shouldCreateHotelId_WhenValidValue() {
        HotelId hotelId = new HotelId("1234aBc");
        assertEquals("1234aBc", hotelId.value());
    }

    @Test
    void shouldThrowException_WhenNull() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new HotelId(null)
        );
        assertEquals(ErrorMessages.HOTELID_EMPTY, ex.getMessage());
    }

    @Test
    void shouldThrowException_WhenBlank() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new HotelId("   ")
        );
        assertEquals(ErrorMessages.HOTELID_EMPTY, ex.getMessage());
    }

    @Test
    void equals_shouldReturnFalseWhenOtherObjectIsDifferentType() {
        HotelId hotelId = new HotelId("abc");
        Object other = "notAHotelId";
        assertNotEquals(hotelId, other);
    }
}
