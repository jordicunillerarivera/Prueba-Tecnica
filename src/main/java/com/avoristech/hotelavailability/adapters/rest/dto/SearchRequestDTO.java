package com.avoristech.hotelavailability.adapters.rest.dto;

import jakarta.validation.constraints.*;

import java.util.List;

public record SearchRequestDTO(
        @NotBlank
        String hotelId,

        @NotBlank
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Formato debe ser DD/MM/YYYY")
        String checkIn,

        @NotBlank
        @Pattern(regexp = "\\d{2}/\\d{2}/\\d{4}", message = "Formato debe ser DD/MM/YYYY")
        String checkOut,

        @NotNull
        @Size(min = 1)
        List<@Min(0) Integer> ages
) {
}
