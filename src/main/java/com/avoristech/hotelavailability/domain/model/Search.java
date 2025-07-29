package com.avoristech.hotelavailability.domain.model;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;

import java.util.*;

public record Search(
        String searchId,
        HotelId hotelId,
        SearchPeriod period,
        List<Integer> ages
) {
    // Factory estatico para crear un search con nuevo id
    public static Search of(HotelId hotelId, SearchPeriod period, List<Integer> ages) {
        return new Search(UUID.randomUUID().toString(), hotelId, period, ages);
    }

    public Search(String searchId, HotelId hotelId, SearchPeriod period, List<Integer> ages) {
        // Validacion de no null
        Objects.requireNonNull(searchId, ErrorMessages.SEARCHID_NULL);
        Objects.requireNonNull(hotelId, ErrorMessages.HOTELID_NULL);
        Objects.requireNonNull(period, ErrorMessages.PERIOD_NULL);
        Objects.requireNonNull(ages, ErrorMessages.AGES_NULL);

        // Validamos que haya almenos una edad
        if (ages.isEmpty()) {
            throw new IllegalArgumentException(ErrorMessages.AGES_EMPTY);
        }

        // Despues de validaciones asignamos los valores finales
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.period = period;
        this.ages = List.copyOf(ages);
    }

    public String getSearchId() {
        return searchId;
    }

    public HotelId getHotelId() {
        return hotelId;
    }

    public SearchPeriod getPeriod() {
        return period;
    }

    public List<Integer> getAges() {
        return ages;
    }

    // Comparamos con otro objeto Search sin tener en cuenta la lista de edades
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Search other)) return false;
        return hotelId.equals(other.hotelId)
                && period.equals(other.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, period, ages.stream().sorted().toList());
    }
}
