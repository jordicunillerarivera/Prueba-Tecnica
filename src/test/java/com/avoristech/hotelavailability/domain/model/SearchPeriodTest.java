package com.avoristech.hotelavailability.domain.model;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchPeriodTest {
    @Test
    void constructor_ThrowsWhenCheckInEqualsCheckOut() {
        String date = "10/10/2026";
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new SearchPeriod(date, date)
        );
        assertEquals(
                ErrorMessages.CHECKIN_OLDER_CHECKOUT,
                ex.getMessage()
        );
    }

    @Test
    void constructor_ThrowsWhenCheckInAfterCheckOut() {
        String checkIn  = "12/10/2026";
        String checkOut = "10/10/2026";
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> new SearchPeriod(checkIn, checkOut)
        );
        assertEquals(
                ErrorMessages.CHECKIN_OLDER_CHECKOUT,
                ex.getMessage()
        );
    }

    @Test
    void constructor_AllowsValidPeriod() {
        // Control: no exception para fechas correctas
        assertDoesNotThrow(() ->
                new SearchPeriod("09/10/2026", "10/10/2026")
        );
    }

    @Test
    void equals_SameInstance_ReturnsTrue() {
        SearchPeriod p = new SearchPeriod("01/01/2026", "02/01/2026");
        assertEquals(p, p);
        assertEquals(p.hashCode(), p.hashCode());
    }

    @Test
    void equals_IdenticalValues_ReturnsTrue() {
        SearchPeriod p1 = new SearchPeriod("01/01/2026", "02/01/2026");
        SearchPeriod p2 = new SearchPeriod("01/01/2026", "02/01/2026");
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
    }

    @Test
    void equals_DifferentType_ReturnsFalse() {
        SearchPeriod p = new SearchPeriod("01/01/2026", "02/01/2026");
        assertNotEquals( "not a period", p);
    }

    @Test
    void equals_Null_ReturnsFalse() {
        SearchPeriod p = new SearchPeriod("01/01/2026", "02/01/2026");
        assertNotEquals(null, p);
    }

    @Test
    void equals_DifferentCheckIn_ReturnsFalse() {
        SearchPeriod p1 = new SearchPeriod("01/01/2026", "02/01/2026");
        SearchPeriod p2 = new SearchPeriod("02/01/2026", "03/01/2026");
        assertNotEquals(p1, p2);
    }

    @Test
    void equals_DifferentCheckOut_ReturnsFalse() {
        SearchPeriod p1 = new SearchPeriod("01/01/2026", "02/01/2026");
        SearchPeriod p2 = new SearchPeriod("01/01/2026", "05/01/2026");
        assertNotEquals(p1, p2);
    }

    @Test
    void equals_ReturnsFalseWhenComparedWithDifferentType() {
        SearchPeriod period = new SearchPeriod("01/01/2026", "02/01/2026");
        assertNotEquals("not a SearchPeriod", period,
                "equals debe retornar false si el objeto no es instancia de SearchPeriod");
    }

    @Test
    void equals_ReturnsTrueWhenSameReference() {
        SearchPeriod period = new SearchPeriod("05/05/2026", "06/05/2026");
        assertEquals(period, period,
                "equals debe retornar true cuando se compara la misma referencia");
    }

    @Test
    void equals_ReturnsFalseWhenDifferentDates() {
        SearchPeriod p1 = new SearchPeriod("10/10/2026", "12/10/2026");
        SearchPeriod p2 = new SearchPeriod("11/10/2026", "13/10/2026");
        assertNotEquals(p1, p2,
                "equals debe retornar false cuando las fechas difieren");
    }

    @Test
    void equals_ReturnsTrueWhenEqualDates() {
        SearchPeriod p1 = new SearchPeriod("15/08/2026", "18/08/2026");
        SearchPeriod p2 = new SearchPeriod("15/08/2026", "18/08/2026");
        assertEquals(p1, p2,
                "equals debe retornar true cuando las fechas coinciden");
    }
}
