package com.avoristech.hotelavailability.domain.model;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchTest {
    @Test
    void of_withEmptyAges_ThrowsIllegalArgumentException() {
        HotelId hotelId = new HotelId("h-empty");
        SearchPeriod period = new SearchPeriod("01/01/2026", "03/01/2026");

        Throwable exception = assertThrows(
                IllegalArgumentException.class,
                () -> Search.of(hotelId, period, List.of())
        );

        assertEquals(
                ErrorMessages.AGES_EMPTY,
                exception.getMessage()
        );
    }

    @Test
    void of_withNullAges_throwsNullPointerException() {
        HotelId hotelId = new HotelId("h-nulll");
        SearchPeriod period = new SearchPeriod("01/01/2026", "03/01/2026");

        assertThrows(NullPointerException.class, () ->
                Search.of(hotelId, period, null));
    }


    @Test
    void equals_SameInstance_ReturnsTrue() {
        Search s = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2026","02/01/2026"), List.of(1,2));
        assertEquals(s, s);
    }

    @Test
    void equals_DifferentType_ReturnsFalse() {
        Search s = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2026","02/01/2026"), List.of(1,2));
        assertNotEquals("not a search", s);
    }

    @Test
    void equals_SameContentDifferentOrderAges_ReturnsTrue() {
        Search a = Search.of(new HotelId("hX"), new SearchPeriod("10/10/2026","12/10/2026"), List.of(3,5,7));
        // Creamos otro Search con mismo contenido pero edades en distinto orden y mismo ID
        Search b = new Search(a.getSearchId(), a.getHotelId(), a.getPeriod(), List.of(7,3,5));
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void equals_DifferentHotelId_ReturnsFalse() {
        Search a = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2026","02/01/2026"), List.of(1,2));
        Search b = new Search(a.getSearchId(), new HotelId("h2"), a.getPeriod(), a.getAges());
        assertNotEquals(a, b);
    }

    @Test
    void equals_DifferentPeriod_ReturnsFalse() {
        Search a = Search.of(new HotelId("h1"), new SearchPeriod("01/01/2026","02/01/2026"), List.of(1,2));
        // Mismo ID y getHotelId(), pero getPeriod()o distinto
        Search b = new Search(a.getSearchId(), a.getHotelId(),
                new SearchPeriod("03/01/2026","04/01/2026"), a.getAges());
        assertNotEquals(a, b);
    }

    @Test
    void equals_IsSymmetricForAgesDifferentOrder() {
        // Creamos dos búsquedas con las mismas edades en distinto orden
        HotelId hotelId = new HotelId("hotelX");
        SearchPeriod period = new SearchPeriod("01/03/2026", "05/03/2026");
        List<Integer> listA = List.of(1, 2, 3);
        List<Integer> listB = List.of(3, 2, 1);

        Search s1 = new Search("id1", hotelId, period, listA);
        Search s2 = new Search("id1", hotelId, period, listB);

        // Deben ser iguales en ambos sentidos
        assertEquals(s1, s2, "s1 debe igual a s2");
        assertEquals(s2, s1, "s2 debe igual a s1");
    }

    @Test
    void equalsAndHashCode_ShouldBeOrderIndependentForAges() {
        // Datos base
        HotelId hotelId = new HotelId("hotelX");
        SearchPeriod period = new SearchPeriod("10/10/2026", "12/10/2026");

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

    @Test
    void equals_ReturnsFalseWhenComparedWithDifferentType() {
        Search search = Search.of(
                new HotelId("h1"),
                new SearchPeriod("01/01/2026", "02/01/2026"),
                List.of(10, 20)
        );
        assertNotEquals("some string", search,
                "equals debe retornar false si el objeto no es instancia de Search");
    }

    @Test
    void equals_ReturnsTrueWhenSameReference() {
        Search search = Search.of(
                new HotelId("h2"),
                new SearchPeriod("05/05/2026", "06/05/2026"),
                List.of(5, 6)
        );
        assertEquals(search, search,
                "equals debe retornar true cuando se compara la misma referencia");
    }

    @Test
    void equals_ReturnsFalseWhenDifferentContent() {
        Search s1 = Search.of(
                new HotelId("h3"),
                new SearchPeriod("10/10/2026", "12/10/2026"),
                List.of(1, 2, 3)
        );
        // Cambiamos hotelId
        Search s2 = new Search(
                s1.getSearchId(),
                new HotelId("other"),
                s1.getPeriod(),
                s1.getAges()
        );
        assertNotEquals(s1, s2,
                "equals debe retornar false cuando el contenido difiere");
    }
}
