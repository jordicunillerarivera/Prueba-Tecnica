package com.avoristech.hotelavailability.infrastructure.adapters.rest.dto;

public record CountResponseDTO(
        String searchId,
        SearchRequestDTO search,
        long count
) {
}
