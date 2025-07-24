package com.avoristech.hotelavailability.domain.model;

import com.avoristech.hotelavailability.infrastructure.config.constants.ErrorMessages;

import java.util.Objects;

public final class HotelId {
    private final String value;

    // Comprueva que value no esta vacio
    public HotelId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(ErrorMessages.HOTELID_EMPTY);
        }
        this.value=value;
    }

    // Devuelve el valor de value
    public String getHotelId() {
        return value;
    }

    // Compara dos value
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HotelId)) return false;
        HotelId other = (HotelId) o;
        return Objects.equals(value, other.value);
    }

    // Devuelve el hascode de value
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
