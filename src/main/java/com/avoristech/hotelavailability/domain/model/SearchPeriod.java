package com.avoristech.hotelavailability.domain.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class SearchPeriod {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final LocalDate checkIn;
    private final LocalDate checkOut;

    public SearchPeriod(String checkInStr, String checkOutStr) {
        // Se comprueba que no sean null
        Objects.requireNonNull(checkInStr, "checkIn no puede ser null");
        Objects.requireNonNull(checkOutStr, "checkOut no puede ser null");

        // Se formatea
        this.checkIn = LocalDate.parse(checkInStr, FORMATTER);
        this.checkOut = LocalDate.parse(checkOutStr, FORMATTER);

        // Validacion de que checkin va antes que checkout
        if (!this.checkIn.isBefore(this.checkOut)) {
            throw new IllegalArgumentException("checkIn debe se anterior a checkOut");
        }
    }

    public LocalDate getCheckIn () {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    // Compara dos objetos Searchfperiod
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SearchPeriod)) return false;
        SearchPeriod other = (SearchPeriod) o;
        return checkIn.equals(other.checkIn) && checkOut.equals(other.checkOut);
    }

    // Devuelve hascode de checkin y checkout
    @Override
    public int hashCode() {
        return Objects.hash(checkIn,checkOut);
    }
}
