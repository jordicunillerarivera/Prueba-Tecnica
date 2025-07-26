package com.avoristech.hotelavailability.domain.model;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    @Test
    void of_withEmptyAges_ThrowsIllegalArgumentException() {
        HotelId hotelId = new HotelId("h-empty");
        SearchPeriod period = new SearchPeriod("01/01/2025", "03/01/2025");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                Search.of(hotelId, period, List.of()));

        assertEquals(ErrorMessages.AGES_EMPTY, ex.getMessage());
    }

    @Test
    void of_withNullAges_throwsNullPointerException() {
        HotelId hotelId = new HotelId("h-nulll");
        SearchPeriod period = new SearchPeriod("01/01/2025", "03/01/2025");

        assertThrows(NullPointerException.class, () ->
                Search.of(hotelId, period, null));
    }


    @Test
    void equals_SameInstance_ReturnsTrue() {
        Search s = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2025","02/01/2025"), List.of(1,2));
        assertEquals(s, s);
    }

    @Test
    void equals_DifferentType_ReturnsFalse() {
        Search s = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2025","02/01/2025"), List.of(1,2));
        assertNotEquals("not a search", s);
    }

    @Test
    void equals_SameContentDifferentOrderAges_ReturnsTrue() {
        Search a = Search.of(new HotelId("hX"), new SearchPeriod("10/10/2025","12/10/2025"), List.of(3,5,7));
        // Creamos otro Search con mismo contenido pero edades en distinto orden y mismo ID
        Search b = new Search(a.getSearchId(), a.getHotelId(), a.getPeriod(), List.of(7,3,5));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_DifferentHotelId_ReturnsFalse() {
        Search a = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2025","02/01/2025"), List.of(1,2));
        Search b = new Search(a.getSearchId(), new HotelId("h2"), a.getPeriod(), a.getAges());
        assertNotEquals(a, b);
    }

    @Test
    void equals_DifferentPeriod_ReturnsFalse() {
        Search a = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2025","02/01/2025"), List.of(1,2));
        // Mismo ID y getHotelId(), pero getPeriod()o distinto
        Search b = new Search(a.getSearchId(), a.getHotelId(),
                new SearchPeriod("03/01/2025","04/01/2025"), a.getAges());
        assertNotEquals(a, b);
    }

    @Test
    void equals_DifferentAges_ReturnsFalse() {
        Search a = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2025","02/01/2025"), List.of(1,2));
        // Mismo ID, getHotelId() y getPeriod()o, pero edades distintas
        Search b = new Search(a.getSearchId(), a.getHotelId(), a.getPeriod(), List.of(1,3));
        assertNotEquals(a, b);
    }

    @Test
    void equals_IsSymmetricForAgesDifferentOrder() {
        // Creamos dos búsquedas con las mismas edades en distinto orden
        HotelId hotelId = new HotelId("hotelX");
        SearchPeriod period = new SearchPeriod("01/03/2025", "05/03/2025");
        List<Integer> listA = List.of(1, 2, 3);
        List<Integer> listB = List.of(3, 2, 1);

        Search s1 = new Search("id1", hotelId, period, listA);
        Search s2 = new Search("id1", hotelId, period, listB);

        // Deben ser iguales en ambos sentidos
        assertEquals(s1, s2, "s1 debe igual a s2");
        assertEquals(s2, s1, "s2 debe igual a s1");
    }

    @Test
    void equals_FailsWhenAgesSubsetButNotSuperset() {
        // s1 tiene un elemento extra que s2 no
        HotelId hotelId = new HotelId("hotelY");
        SearchPeriod period = new SearchPeriod("10/04/2025", "12/04/2025");
        List<Integer> listFull = List.of(1, 2, 3);
        List<Integer> listPartial = List.of(1, 2);

        Search s1 = new Search("id2", hotelId, period, listFull);
        Search s2 = new Search("id2", hotelId, period, listPartial);

        // ages.containsAll(other) pasa, pero other.containsAll(ages) falla
        assertNotEquals(s1, s2, "s1 no debe igual a s2 (faltan edades en s2)");
        assertNotEquals(s2, s1, "s2 no debe igual a s1 (tiene menos elementos)");
    }

    @Test
    void equalsAndHashCode_ShouldBeOrderIndependentForAges() {
        // Datos base
        HotelId hotelId = new HotelId("hotelX");
        SearchPeriod period = new SearchPeriod("10/10/2025", "12/10/2025");

        // Search en dos órdenes de edades diferentes
        Search s1 = Search.of(hotelId, period, List.of(1, 2, 3));
        Search s2 = new Search(
                s1.getSearchId(),              // mismo ID
                hotelId,
                period,
                List.of(3, 1, 2)            // edades en distinto orden
        );

        assertAll("equals & hashCode",
                () -> assertEquals(s1, s2, "equals debe ser true aunque el orden de edades cambie"),
                () -> assertEquals(s1.hashCode(), s2.hashCode(),
                        "hashCode debe ser igual para búsquedas equivalentes"),
                () -> assertTrue(s1.getAges().containsAll(s2.getAges()) &&
                                s2.getAges().containsAll(s1.getAges()),
                        "las listas de edades deben contener mismos elementos")
        );
    }
}
